package com.capstone.have.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.have.data.response.ActivityData
import com.capstone.have.databinding.FragmentUpcomingActivityBinding
import com.capstone.have.ui.activity.ActivityViewModel

class UpcomingActivityFragment : Fragment() {
    private var _binding : FragmentUpcomingActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var userToken: String
    private val activityViewModel by viewModels<ActivityViewModel>()
    private var activityId: String = ARG_ID



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingActivityBinding.inflate(inflater, container, false)
        val view = binding.root
        userToken = activityViewModel.getUserToken().toString()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            activityId = it.getString(ARG_ID).orEmpty()
        }

        val token = userToken

//        GET ACTIVITY
        activityViewModel.getActivity(activityId, token)

//        SETUP DATA
        activityViewModel.activity.observe(viewLifecycleOwner) { response ->
            response?.data?.let { updateUI(it) }
        }
    }

//    SETUP UI
    private fun updateUI(data: ActivityData) {
        binding.tvActivity.text = data.name
        binding.tvActivityTime.text = data.duration.toString()
    }

//    ANTI MEMORY LEAKS
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ID = "id"
    }
}