package com.capstone.have.ui.menu.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.have.R
import com.capstone.have.data.response.SleepDurationData
import com.capstone.have.databinding.FragmentHealthOverviewBinding
import com.capstone.have.databinding.FragmentSleepBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.menu.calorie.CalorieFragment
import com.capstone.have.ui.menu.sleep.SleepFragment
import com.capstone.have.ui.menu.sleep.SleepViewModel
import kotlinx.coroutines.launch

class HealthOverviewFragment : Fragment() {

    private var _binding : FragmentHealthOverviewBinding? = null
    private val binding get() = _binding!!
    private val sleepViewModel: SleepViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var userToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        setupCardView()

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

        sleepViewModel.sleepDuration.observe(viewLifecycleOwner) { response ->
            response?.data?.let { updateUI(it) }
        }
    }

    private fun updateUI(data: SleepDurationData) {
        binding.tvSleepPercentage.text = "${data.quality}%"
    }

    private fun setupCardView(){
        binding.cardSleepOverview.setOnClickListener{
            startActivity(Intent(requireContext(), SleepFragment::class.java))
            requireActivity().finish()
        }

        binding.cardCalorieOverview.setOnClickListener{
            startActivity(Intent(requireContext(), CalorieFragment::class.java))
            requireActivity().finish()
        }
    }
}