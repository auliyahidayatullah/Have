package com.capstone.have.ui.menu.calorie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.have.R

class CalorieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calorie, container, false)

        childFragmentManager.beginTransaction()
            .replace(R.id.top_calorie_container, BigCaloriesFragment())
            .commit()

        childFragmentManager.beginTransaction()
            .replace(R.id.food_rec_container, FoodRecomFragment())
            .commit()

        return view
    }
}