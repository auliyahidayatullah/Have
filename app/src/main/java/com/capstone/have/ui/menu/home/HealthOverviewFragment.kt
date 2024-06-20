package com.capstone.have.ui.menu.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.have.data.response.CalorieOverviewData
import com.capstone.have.data.response.SleepDurationData
import com.capstone.have.databinding.FragmentHealthOverviewBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.menu.calorie.CalorieViewModel
import com.capstone.have.ui.menu.sleep.SleepViewModel
import kotlinx.coroutines.launch

class HealthOverviewFragment : Fragment() {

    private var _binding : FragmentHealthOverviewBinding? = null
    private val binding get() = _binding!!
    private val sleepViewModel: SleepViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val calorieViewModel: CalorieViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var userToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            sleepViewModel.getUserToken().collect { userModel ->
                userToken = userModel.token
                sleepViewModel.getSleepDuration(userToken)
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            calorieViewModel.getUserToken().collect { userModel ->
                userToken = userModel.token
                calorieViewModel.getCalorieOverview(userToken)
            }
        }

        sleepViewModel.sleepDuration.observe(viewLifecycleOwner) { response ->
            response?.data?.let { updateSleepUI(it) }
        }

        calorieViewModel.calorieOverview.observe(viewLifecycleOwner) { response ->
            response?.data?.let { updateCaloriesUI(it) }
        }
    }

    private fun updateSleepUI(data: SleepDurationData) {
        binding.tvSleepPercentage.text = "${data.quality}%"
    }

    private fun updateCaloriesUI(data: CalorieOverviewData) {
        binding.tvCaloriePercentage.text = data.calories
    }
}