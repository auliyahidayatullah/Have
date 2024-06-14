package com.capstone.have.ui.fragments.calorie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.main.MainViewModel
import com.capstone.have.data.Result
import com.capstone.have.data.response.FoodRecommendationsItem
import com.capstone.have.databinding.FragmentFoodRecomBinding

class FoodRecomFragment : Fragment() {

    private var _binding: FragmentFoodRecomBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodRecomBinding.inflate(inflater, container, false)
        val view = binding.root

        // Mengatur LinearLayoutManager dengan orientasi horizontal
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFoodRec.layoutManager = layoutManager

        // Mengatur DividerItemDecoration untuk orientasi horizontal
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFoodRec.addItemDecoration(itemDecoration)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getListFood().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    setFoodData(result.data)
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed Load Data", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setFoodData(listFood: List<FoodRecommendationsItem>) {
        val adapter = FoodsRecomAdapter()
        adapter.submitList(listFood)
        binding.rvFoodRec.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}