package com.capstone.have.ui.menu.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.have.R
import com.capstone.have.databinding.FragmentActivityBinding

class ActivityFragment : Fragment() {
    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        val view = binding.root

        childFragmentManager.beginTransaction()
            .replace(R.id.container_yourActivity, YourActivityFragment())
            .commit()

        childFragmentManager.beginTransaction()
            .replace(R.id.container_exerciseRecommendation, ExerciseRecFragment())
            .commit()

        return view
    }

}