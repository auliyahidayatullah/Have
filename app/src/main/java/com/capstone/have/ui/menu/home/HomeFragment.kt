package com.capstone.have.ui.menu.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.have.R
import com.capstone.have.databinding.FragmentHomeBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.main.MainActivity
import com.capstone.have.ui.main.MainViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup child fragment
        childFragmentManager.beginTransaction()
            .replace(R.id.upcomingActivity, UpcomingActivityFragment())
            .replace(R.id.user_progress, HealthOverviewFragment())
            .commit()

//        SETUP BTN LOGOUT
        binding.btnLogout.setOnClickListener {
            logout()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout() {
        val activity = activity as MainActivity?
        activity?.logout()
    }

}