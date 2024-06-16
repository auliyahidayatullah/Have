package com.capstone.have.ui.menu.sleep

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
import com.google.android.material.timepicker.TimeFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
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
            showTimePicker { selectedTime24, selectedTime12 ->
                if (selectedTime24.isNotBlank()) {
                    binding.itemCard.textTime1.text = selectedTime12
                    val currentWakeUpTime = binding.itemCard.textTime2.text.toString()
                    viewModel.addSleep(selectedTime24, convertTo24HourFormat(currentWakeUpTime))
                    calculateSleepDuration(selectedTime24, convertTo24HourFormat(currentWakeUpTime))
                } else {
                    showToast("Invalid bedtime selected")
                }
            }
        }

        binding.itemCard.cardViewWakeup.setOnClickListener {
            showTimePicker { selectedTime24, selectedTime12 ->
                if (selectedTime24.isNotBlank()) {
                    binding.itemCard.textTime2.text = selectedTime12
                    val currentBedTime = binding.itemCard.textTime1.text.toString()
                    viewModel.addSleep(convertTo24HourFormat(currentBedTime), selectedTime24)
                    calculateSleepDuration(convertTo24HourFormat(currentBedTime), selectedTime24)
                } else {
                    showToast("Invalid wakeup time selected")
                }
            }
        }
    }

    private fun calculateSleepDuration(bedtime: String, wakeupTime: String) {
        if (bedtime.isBlank() || wakeupTime.isBlank()) {
            // Handle empty bedtime or wakeupTime strings
            return
        }

        try {
            val (bedHour, bedMinute) = bedtime.split(":").map { it.toInt() }
            val (wakeupHour, wakeupMinute) = wakeupTime.split(":").map { it.toInt() }

            var hours = wakeupHour - bedHour
            var minutes = wakeupMinute - bedMinute

            if (minutes < 0) {
                hours -= 1
                minutes += 60
            }

            if (hours < 0) {
                hours += 24
            }

            val formattedDuration = if (hours > 0 && minutes > 0) {
                "$hours h $minutes m"
            } else if (hours > 0) {
                "$hours h"
            } else {
                "$minutes m"
            }

            binding.sleepHour.text = formattedDuration

            val idealSleepHours = 8.0
            val actualSleepHours = hours + (minutes / 60.0)
            val sleepQualityPercentage = (actualSleepHours / idealSleepHours) * 100
            val displayedSleepQuality = if (sleepQualityPercentage > 100) 100.0 else sleepQualityPercentage

            binding.sleepPercentage.text = String.format(Locale.getDefault(), "%.0f%%", displayedSleepQuality)
            binding.progressSleep.progress = displayedSleepQuality.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            showToast("Error calculating sleep duration")
        }
    }

    private fun showTimePicker(onTimeSelected: (String, String) -> Unit) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(10)
            .setMinute(0)
            .build()

        picker.show(childFragmentManager, "TIME_PICKER")

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            val formattedTime12 = String.format(
                Locale.getDefault(),
                "%02d:%02d %s",
                if (hour == 0 || hour == 12) 12 else hour % 12,
                minute,
                if (hour < 12) "AM" else "PM"
            )
            val formattedTime24 = String.format(
                Locale.getDefault(),
                "%02d:%02d",
                hour,
                minute
            )
            onTimeSelected(formattedTime24, formattedTime12)
        }
    }

    private fun convertTo24HourFormat(timeString: String): String {
        if (timeString.isBlank()) return ""

        val inputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return try {
            val date: Date? = inputFormat.parse(timeString)
            date?.let {
                outputFormat.format(it)
            } ?: ""
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
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
        viewModel.addSleepResult.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}
