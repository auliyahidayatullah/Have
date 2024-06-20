package com.capstone.have.ui.menu.sleep

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.capstone.have.databinding.FragmentSleepStatisticBinding
import com.capstone.have.ui.ViewModelFactory
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.launch

class SleepStatisticFragment : Fragment() {

    private var _binding: FragmentSleepStatisticBinding? = null
    private val binding get() = _binding!!
    private lateinit var sleepViewModel: SleepViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSleepStatisticBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        sleepViewModel = ViewModelProvider(this, factory)[SleepViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            sleepViewModel.getUserToken().collect { userModel ->
                sleepViewModel.getSleepData(userModel.token)
            }
        }

        sleepViewModel.chartData.observe(viewLifecycleOwner) { entries ->
            updateChart(entries)
        }

        sleepViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            showError(errorMessage)
        }
    }

    private fun updateChart(entries: List<BarEntry>) {
        binding.errorMessage.visibility = View.GONE
        binding.sleepChart.visibility = View.VISIBLE

        val barDataSet = BarDataSet(entries, "Sleep").apply {
            setColors(*ColorTemplate.MATERIAL_COLORS) // Apply colors to the bars
            valueTextColor = Color.BLACK
            valueTextSize = 10f
        }

        val barData = BarData(barDataSet)
        binding.sleepChart.data = barData

        // Add a limit line
        val limitLine = LimitLine(2000f, "Daily Limit")
        limitLine.lineWidth = 2f
        limitLine.enableDashedLine(10f, 10f, 0f)
        limitLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        limitLine.textSize = 10f

        val yAxis = binding.sleepChart.axisLeft
        yAxis.addLimitLine(limitLine)

        binding.sleepChart.invalidate() // refresh chart
    }

    private fun showError(message: String) {
        binding.sleepChart.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = message
    }
}
