package com.capstone.have.ui.menu.calorie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.have.R
import com.capstone.have.data.response.AddCalorieResponse
import com.capstone.have.data.retrofit.ApiConfig
import com.capstone.have.databinding.ActivityAddFoodBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.data.Result
import com.capstone.have.ui.main.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

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

        binding.btnAddCalories.setOnClickListener (this)

        observeViewModel()
        userToken = caloriesViewModel.getUserToken().toString()

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
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

    private fun addCalories() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val imagePart = MultipartBody.Part.createFormData("photo", imageFile.name, imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull()))
            val foodName = binding.edFoodName.text.toString().toRequestBody("text/plain")
            val calories = binding.edCalories.text.toString().toRequestBody("text/plain")
            caloriesViewModel.addCalories(imagePart, foodName, calories)
            showLoading(true)

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService(userToken)
                    val successResponse = apiService.addCalories(imagePart, foodName, calories)
                    showToast(successResponse.message.toString())
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, AddCalorieResponse::class.java)
                    showToast(errorResponse.message.toString())
                    showLoading(false)
                }
            }
        }
    }

    private fun observeViewModel() {
        caloriesViewModel.uploadResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "Upload success!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CalorieFragment::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_FOOD_NAME = "extra_food_name"
        const val EXTRA_CALORIES = "extra_calories"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_addCalories) {
            val foodName = binding.edFoodName.text.toString().trim()
            val calories = binding.edCalories.text.toString().trim()

            if (foodName.isNotBlank() && calories.isNotBlank()) {
                addCalories()

            } else {
                Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
