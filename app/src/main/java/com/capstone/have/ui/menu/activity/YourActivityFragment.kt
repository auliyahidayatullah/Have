package com.capstone.have.ui.menu.activity

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
import com.capstone.have.data.response.AllactivityItem
import com.capstone.have.databinding.FragmentYourActivityBinding
import com.capstone.have.ui.ViewModelFactory


class YourActivityFragment : Fragment() {

    private var _binding: FragmentYourActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYourActivityBinding.inflate(inflater, container, false)
        val view = binding.root

//        SET RV HORIZONTAL
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvYourActivity.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvYourActivity.addItemDecoration(itemDecoration)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        activityViewModel = ViewModelProvider(this, factory)[ActivityViewModel::class.java]

        activityViewModel.getYourActivity().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    setActivityData(result.data)
                }
                is Result.Error -> {
                    Toast.makeText(context, "Failed Load Data", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

    private fun setActivityData(listActivity: List<AllactivityItem>) {
        val adapter = ActivityAdapter()
        adapter.submitList(listActivity)
        binding.rvYourActivity.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}