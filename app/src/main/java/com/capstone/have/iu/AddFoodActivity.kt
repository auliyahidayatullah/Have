package com.capstone.have.iu

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.capstone.have.R
import com.capstone.have.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity() {

private lateinit var binding: ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityAddFoodBinding.inflate(layoutInflater)
     setContentView(binding.root)
    }
}