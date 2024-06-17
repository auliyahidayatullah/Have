package com.capstone.have.ui.menu.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.capstone.have.R
import com.capstone.have.data.response.UpcomingActivityData
import com.capstone.have.databinding.FragmentUpcomingActivityBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.menu.activity.ActivityViewModel
import kotlinx.coroutines.launch

class UpcomingActivityFragment : Fragment() {
    private var _binding: FragmentUpcomingActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var userToken: String
    private lateinit var activityViewModel: ActivityViewModel
    private var activityId: String = ARG_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        activityViewModel = ViewModelProvider(this, factory)[ActivityViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            activityViewModel.getUserToken().collect { userModel ->
                userToken = userModel.token

                arguments?.let {
                    activityId = it.getString(ARG_ID).orEmpty()
                }

                val token = userToken
                activityViewModel.getUpcomingActivity(activityId, token)
            }
        }

        // Observe LiveData outside the launch block
        activityViewModel.activity.observe(viewLifecycleOwner) { response ->
            response?.data?.let { updateUI(it) }
        }
    }

    private fun updateUI(data: UpcomingActivityData) {
        binding.iconActivity.setImageResource(R.drawable.ic_activity)
        binding.iconTime.setImageResource(R.drawable.ic_time)
        binding.tvActivity.text = data.name
        binding.tvActivityTime.text = data.duration.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ID = "id"
    }
}