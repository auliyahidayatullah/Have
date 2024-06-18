package com.capstone.have.ui.menu.calorie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.have.data.Result
import com.capstone.have.data.response.DataItem
import com.capstone.have.databinding.FragmentBigCaloriesBinding
import com.capstone.have.ui.ViewModelFactory

class BigCaloriesFragment : Fragment() {
    private var _binding: FragmentBigCaloriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var calorieViewModel: CalorieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBigCaloriesBinding.inflate(inflater, container, false)
        val view = binding.root

//        SET RV HORIZONTAL
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBigCalories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvBigCalories.addItemDecoration(itemDecoration)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        calorieViewModel = ViewModelProvider(this, factory)[CalorieViewModel::class.java]

        calorieViewModel.getBigCalories().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    setBigCaloriesData(result.data)
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed Load Data", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

    private fun setBigCaloriesData(listBigCalories: List<DataItem>) {
        val adapter = BigCalorieAdapter()
        adapter.submitList(listBigCalories)
        binding.rvBigCalories.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}