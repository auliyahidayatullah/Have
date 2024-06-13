package com.capstone.have.ui.fragments.calorie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.have.data.repository.CalorieRepository
import com.capstone.have.data.retrofit.ApiConfig
import com.capstone.have.data.retrofit.Injection
import com.capstone.have.databinding.FragmentFoodRecomBinding

class FoodRecomFragment : Fragment() {
    private lateinit var binding: FragmentFoodRecomBinding
    private val viewModel: FoodRecomViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory().create(FoodRecomViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodRecomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodRecomAdapter()
        binding.rvActivity.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvActivity.adapter = adapter

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVzZXItYnQyVm1qRGZiczNvd2lEbyIsImlhdCI6MTcxODE4Mzg3Nn0.A3DmZEXevRUbXTHsIksJ3dd_2iGysc3LOFbxtB_vakc"

        val apiService = ApiConfig.getApiService(token)

        val repository = Injection.provideCalorieRepository(apiService)

        viewModel.foodRecommendations.observe(viewLifecycleOwner) { foods ->
            adapter.submitList(foods)
        }

        viewModel.getFoodRecommendations(token)
    }
}
