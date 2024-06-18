package com.capstone.have.ui.menu.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.have.R
import com.capstone.have.databinding.FragmentHealthOverviewBinding
import com.capstone.have.databinding.FragmentSleepBinding
import com.capstone.have.ui.menu.calorie.CalorieFragment
import com.capstone.have.ui.menu.sleep.SleepFragment

class HealthOverviewFragment : Fragment() {

    private var _binding : FragmentHealthOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthOverviewBinding.inflate(inflater, container, false)
        val view = binding.root

        setupCardView()

        return view
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