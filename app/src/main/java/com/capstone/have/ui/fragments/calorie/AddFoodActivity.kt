package com.capstone.have.ui.fragments.calorie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity() {

private lateinit var binding: ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityAddFoodBinding.inflate(layoutInflater)
     setContentView(binding.root)
    }
}