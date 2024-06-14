package com.capstone.have.ui.fragments.calorie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.have.data.preference.UserPreference
import com.capstone.have.data.preference.dataStore
import com.capstone.have.databinding.FragmentFoodRecomBinding
import com.capstone.have.ui.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FoodRecomFragment : Fragment() {
    private var _binding: FragmentFoodRecomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FoodRecomViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodRecomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodRecomAdapter()
        binding.rvActivity.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvActivity.adapter = adapter

        // Ambil token dari sesi
        viewLifecycleOwner.lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(requireContext().dataStore)
            val userSession = userPreference.getSession().first()
            val token = userSession.token

            viewModel.foodRecommendations.observe(viewLifecycleOwner) { foods ->
                adapter.submitList(foods)
            }

            viewModel.getFoodRecommendations(token)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
