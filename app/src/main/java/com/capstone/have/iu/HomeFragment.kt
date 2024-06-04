package com.capstone.have.iu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.have.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        // Setup child fragment
        childFragmentManager.beginTransaction()
            .replace(R.id.upcomingActivity, UpcomingActivityFragment())
            .commit()

        childFragmentManager.beginTransaction()
            .replace(R.id.card_container, ExerciseRecFragment())
            .commit()

        return view
    }
}