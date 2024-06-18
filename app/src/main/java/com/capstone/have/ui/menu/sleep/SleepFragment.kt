package com.capstone.have.ui.menu.sleep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.have.R
import com.capstone.have.data.response.SleepDurationData
import com.capstone.have.databinding.FragmentSleepBinding
import com.capstone.have.ui.ViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.*
import kotlinx.coroutines.launch
import java.util.Locale

class SleepFragment : Fragment() {

    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!
    private val sleepViewModel: SleepViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var userToken: String

    // Tambahkan variabel untuk melacak status klik
    private var isBedtimeClicked = false
    private var isWakeupClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSleepBinding.inflate(inflater, container, false)
        val view = binding.root

        childFragmentManager.beginTransaction()
            .replace(R.id.sleep_statistic, SleepStatisticFragment())
            .commit()

        setupCardViewClicks()

        return view
    }

    private fun setupCardViewClicks() {
        binding.itemCard.cardViewBedtime.setOnClickListener {
            showTimePicker { selectedTime ->
                binding.itemCard.textTime1.text = selectedTime
                val currentWakeUpTime = binding.itemCard.textTime2.text.toString()
                sleepViewModel.addSleep(selectedTime, currentWakeUpTime)

                // Update status klik
                isBedtimeClicked = true
                checkAndObserveViewModel()
            }
        }

        binding.itemCard.cardViewWakeup.setOnClickListener {
            showTimePicker { selectedTime ->
                binding.itemCard.textTime2.text = selectedTime
                val currentBedTime = binding.itemCard.textTime1.text.toString()
                sleepViewModel.addSleep(currentBedTime, selectedTime)

                // Update status klik
                isWakeupClicked = true
                checkAndObserveViewModel()
            }
        }
    }

    // Fungsi untuk memeriksa apakah kedua card item telah diklik
    private fun checkAndObserveViewModel() {
        if (isBedtimeClicked && isWakeupClicked) {
            observeViewModel()
        }
    }

    private fun updateUI(data: SleepDurationData) {
        binding.sleepPercentage.text = data.quality
        binding.sleepHour.text = "${data.hours}h ${data.minutes}m"
    }

    private fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_12H)
            .setHour(10)
            .setMinute(0)
            .build()

        picker.show(childFragmentManager, "TIME_PICKER")

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            val formattedTime = String.format(
                Locale.getDefault(),
                "%02d:%02d %s",
                if (hour == 0 || hour == 12) 12 else hour % 12,
                minute,
                if (hour >= 12) "PM" else "AM"
            )
            onTimeSelected(formattedTime)
        }
    }

    private fun observeViewModel() {
        sleepViewModel.addSleepResult.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                "fail" -> {
                    showToast("Failed to add sleep: ${result.message}")
                }
                else -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        sleepViewModel.getUserToken().collect { userModel ->
                            userToken = userModel.token
                            sleepViewModel.getSleepDuration(userToken)
                        }
                    }

                    sleepViewModel.sleepDuration.observe(viewLifecycleOwner) { response ->
                        response?.data?.let { updateUI(it) }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}