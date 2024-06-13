package com.capstone.have.ui.fragments.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.have.R
import com.capstone.have.databinding.FragmentHomeBinding
import com.capstone.have.ui.fragments.activity.ExerciseRecFragment
import com.capstone.have.ui.main.MainActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup child fragment
        childFragmentManager.beginTransaction()
            .replace(R.id.upcomingActivity, UpcomingActivityFragment())
            .commit()

        childFragmentManager.beginTransaction()
            .replace(R.id.user_progress, ExerciseRecFragment())
            .commit()

        binding.btnLogout.setOnClickListener {
            logout()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout() {
        val mainActivity = activity as MainActivity
        mainActivity.logout()
    }
}