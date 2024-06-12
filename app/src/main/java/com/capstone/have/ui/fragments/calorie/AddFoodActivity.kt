package com.capstone.have.ui.fragments.calorie

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.R
import com.capstone.have.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString: String? = intent.getStringExtra("imageUri")
        imageUriString?.let {
            val imageUri = Uri.parse(it)
            binding.imageView.setImageURI(imageUri)
        } ?: run {
            binding.imageView.setImageResource(R.drawable.ic_placeholder)
        }

        val calories: String? = intent.getStringExtra("calories")
        calories?.let {
            binding.edCalories.setText(it)
        }
    }
}
