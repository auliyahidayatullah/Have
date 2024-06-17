package com.capstone.have.ui.menu.calorie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.CalorieRepository
import com.capstone.have.data.response.AddCalorieResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.capstone.have.data.Result
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class CalorieViewModel (private val calorieRepository: CalorieRepository, private val userRepository: UserRepository) : ViewModel() {
    private val _uploadResult = MutableLiveData<Result<AddCalorieResponse>>()
    val uploadResult: LiveData<Result<AddCalorieResponse>> = _uploadResult

    fun addCalories(file: MultipartBody.Part, foodName: RequestBody, calories: RequestBody) {
        viewModelScope.launch {
            try {
                val response = calorieRepository.addCalories(file, foodName, calories)
                _uploadResult.value = Result.Success(response)

            } catch (e: Exception) {
                _uploadResult.value = Result.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun getUserToken(): Flow<UserModel> {
        return userRepository.getSession()
    }
}