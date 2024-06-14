package com.capstone.have.ui.fragments.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.capstone.have.R
import com.capstone.have.databinding.FragmentHomeBinding
import com.capstone.have.ui.LandingActivity
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.fragments.activity.ExerciseRecFragment
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
            .commit()

        childFragmentManager.beginTransaction()
            .replace(R.id.user_progress, ExerciseRecFragment())
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

        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LandingActivity::class.java))
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout (){
        val sharedPreferences = requireActivity().getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Navigate to LoginActivity and clear the back stack
        val intent = Intent(activity, LandingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        // Finish the current activity
        activity?.finish()
    }

    companion object {
        private const val USER_PREFERENCE = "user_preferences"
    }
}