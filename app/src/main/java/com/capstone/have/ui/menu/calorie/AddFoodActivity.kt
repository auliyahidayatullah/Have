package com.capstone.have.ui.menu.calorie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.have.R
import com.capstone.have.databinding.ActivityAddFoodBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.data.Result
import com.capstone.have.ui.main.MainActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddFoodActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddFoodBinding
    private var currentImageUri: Uri? = null
    private lateinit var userToken: String

    private val caloriesViewModel by viewModels<CalorieViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddCalories.setOnClickListener(this)

        observeViewModel()

        // Observe user token
        lifecycleScope.launch {
            caloriesViewModel.getUserToken().collect { user ->
                userToken = user.token
            }
        }

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            currentImageUri = it
            binding.imageView.setImageURI(it)
        } ?: run {
            binding.imageView.setImageResource(R.drawable.ic_placeholder)
        }

        val caloriesInt = intent.getIntExtra(EXTRA_CALORIES, 0)
        val calories = caloriesInt.toString()
        binding.edCalories.setText(calories)

        val foodName: String? = intent.getStringExtra(EXTRA_FOOD_NAME)
        foodName?.let {
            binding.edFoodName.setText(it)
        }

        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun addCalories() {
        Log.d("AddFoodActivity", "addCalories() called")
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull()))

            val foodName = binding.edFoodName.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val calories = binding.edCalories.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            caloriesViewModel.addCalories(imagePart, foodName, calories)
        } ?: Log.d("AddFoodActivity", "currentImageUri is null")
    }

    private fun observeViewModel() {
        caloriesViewModel.uploadResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    Log.d("AddFoodActivity", "Upload success!")
                    Toast.makeText(this, "Upload success!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Failed Load Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_addCalories) {
            Log.d("AddFoodActivity", "Button clicked")
            val foodName = binding.edFoodName.text.toString().trim()
            val calories = binding.edCalories.text.toString().trim()

            if (foodName.isNotBlank() && calories.isNotBlank()) {
                addCalories()
            } else {
                Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val EXTRA_FOOD_NAME = "extra_food_name"
        const val EXTRA_CALORIES = "extra_calories"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}
