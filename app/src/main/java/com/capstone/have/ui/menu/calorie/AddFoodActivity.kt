package com.capstone.have.ui.menu.calorie

import android.content.Intent
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

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            binding.imageView.setImageURI(it)
        } ?: run {
            binding.imageView.setImageResource(R.drawable.ic_placeholder)
        }

        val calories: String? = intent.getStringExtra(EXTRA_CALORIES)
        calories?.let {
            binding.edCalories.setText(it)
        }

        val foodName: String? = intent.getStringExtra(EXTRA_FOOD_NAME)
        foodName?.let {
            binding.edFoodName.setText(it)
        }

        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, CalorieFragment::class.java))
            finish()
        }
    }

    companion object {
        const val EXTRA_FOOD_NAME = "extra_food_name"
        const val EXTRA_CALORIES = "extra_calories"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }

}
