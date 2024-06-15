package com.capstone.have.ui.fragments.sleep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.have.R
import com.capstone.have.databinding.FragmentSleepBinding
import com.capstone.have.ui.ViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.*
import java.util.Locale

class SleepFragment : Fragment() {

    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SleepViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSleepBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup child fragment
        childFragmentManager.beginTransaction()
            .replace(R.id.statistic_container, SleepStatisticFragment())
            .commit()

        setupCardViewClicks()
        observeViewModel()

        return view
    }

    private fun setupCardViewClicks() {
        binding.itemCard.cardViewBedtime.setOnClickListener {
            showTimePicker { selectedTime ->
                binding.itemCard.textTime1.text = selectedTime
                val currentWakeUpTime = binding.itemCard.textTime2.text.toString()
                viewModel.addSleep(selectedTime, currentWakeUpTime)
            }
        }

        binding.itemCard.cardViewWakeup.setOnClickListener {
            showTimePicker { selectedTime ->
                binding.itemCard.textTime2.text = selectedTime
                val currentBedTime = binding.itemCard.textTime1.text.toString()
                viewModel.addSleep(currentBedTime, selectedTime)
            }
        }
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
        viewModel.addSleepResult.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                "failed" -> {
                    showToast("Failed to add sleep: ${result.message}")
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
