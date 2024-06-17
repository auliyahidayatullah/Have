package com.capstone.have.ui.menu.calorie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.CalorieRepository
import com.capstone.have.data.response.AddCalorieResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.capstone.have.data.Result
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.CaloriesItem
import com.capstone.have.data.response.DataItem
import com.capstone.have.data.retrofit.ApiConfig
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.flow.Flow

class CalorieViewModel (private val calorieRepository: CalorieRepository, private val userRepository: UserRepository) : ViewModel() {
    private val _uploadResult = MutableLiveData<Result<AddCalorieResponse>>()
    val uploadResult: LiveData<Result<AddCalorieResponse>> = _uploadResult
    private val _chartData = MutableLiveData<List<Entry>>()
    val chartData: LiveData<List<Entry>> get() = _chartData
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getCalorieData(token: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService(token).getCalories()
                val entries = response.data?.calories?.mapIndexed { index, item ->
                    item?.calories?.let { Entry(index.toFloat(), it.toFloat()) }
                }?.filterNotNull() ?: emptyList()
                _chartData.postValue(entries)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            }
        }
    }

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

    fun getBigCalories(): LiveData<Result<List<DataItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = calorieRepository.getBigCalories()
            if (response.status == "fail") {
                emit(Result.Error(response.message ?: "Unknown error"))
            } else {
                emit(Result.Success(response.bigCaloriesData.filterNotNull()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Network error"))
        }
    }

    fun getUserToken(): Flow<UserModel> {
        return userRepository.getSession()
    }
}