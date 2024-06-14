package com.capstone.have.ui.fragments.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.have.data.Result
import com.capstone.have.data.response.ActivityData
import com.capstone.have.databinding.FragmentExerciseRecBinding
import com.capstone.have.ui.ViewModelFactory


class YourActivityFragment : Fragment() {

    private var _binding: FragmentExerciseRecBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var userToken: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseRecBinding.inflate(inflater, container, false)
        val view = binding.root

        // Mengatur LinearLayoutManager dengan orientasi horizontal
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvExercise.layoutManager = layoutManager

        // Mengatur DividerItemDecoration untuk orientasi horizontal
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvExercise.addItemDecoration(itemDecoration)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        activityViewModel = ViewModelProvider(this, factory)[ActivityViewModel::class.java]

//        activityViewModel.getActivity().observe(viewLifecycleOwner) { result ->
//            userToken = userModel.token
//            when (result) {
//                is Result.Success -> {
//                    setExerciseData(result.data)
//                }
//                is Result.Error -> {
//                    Toast.makeText(context, "Failed Load Data", Toast.LENGTH_SHORT).show()
//                }
//                else -> {}
//            }
//        }

    }

    private fun setExerciseData(listActivity: List<ActivityData>) {
        val adapter = ActivityAdapter()
        adapter.submitList(listActivity)
        binding.rvExercise.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}