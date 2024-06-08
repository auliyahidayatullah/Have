package com.capstone.have.ui.menu.sleep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.have.R

class SleepFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sleep, container, false)

        // Setup child fragment
        childFragmentManager.beginTransaction()
            .replace(R.id.statistic_container, SleepStatisticFragment())
            .commit()

        return view
    }
}
