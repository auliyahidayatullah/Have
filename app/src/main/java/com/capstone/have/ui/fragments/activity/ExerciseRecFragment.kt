package com.capstone.have.ui.fragments.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.have.databinding.FragmentExerciseRecBinding

class ExerciseRecFragment : Fragment() {

    private var _binding : FragmentExerciseRecBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseRecBinding.inflate(inflater, container, false)
        val view = binding.root

//        val layoutManager = LinearLayoutManager(requireActivity())
//        binding.rv.layoutManager = layoutManager
//
//        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
//        binding.rvFollow.addItemDecoration(itemDecoration)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}