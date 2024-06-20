package com.capstone.have.ui.menu.sleep

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.SleepRepository
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.AddSleepResponse
import com.capstone.have.data.response.SleepDurationResponse
import com.capstone.have.data.retrofit.ApiConfig
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SleepViewModel(private val userRepository: UserRepository, private val repository: SleepRepository) : ViewModel() {

    val bedTime = MutableLiveData<String>()
    val wakeUpTime = MutableLiveData<String>()

    private val _addSleepResult = MutableLiveData<AddSleepResponse>()
    val addSleepResult: LiveData<AddSleepResponse> = _addSleepResult
    private val _sleepDuration = MutableLiveData<SleepDurationResponse>()
    val sleepDuration: LiveData<SleepDurationResponse> = _sleepDuration
    private val _chartData = MutableLiveData<List<BarEntry>>()
    val chartData: LiveData<List<BarEntry>> get() = _chartData
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun addSleep(bedtime: String, wakeuptime: String) {
        viewModelScope.launch {
            try {
                val response = repository.addSleep(bedtime, wakeuptime)
                _addSleepResult.postValue(response)
            } catch (e: Exception) {
                _addSleepResult.postValue(AddSleepResponse(status = "failed", message = e.message))
            }
        }
    }

    fun getSleepDuration(token: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService(token).getSleepDuration()
                Log.d("API Response", response.toString())
                _sleepDuration.postValue(response)
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
                _sleepDuration.postValue(SleepDurationResponse(status = "fail", message = e.message))
            }
        }
    }

    fun getSleepData(token: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService(token).getSleep()
                if (response.status == "success") {
                    val entries = response.data.mapIndexed { index, item ->
                        try {
                            item?.quality?.toFloat()?.let { BarEntry(index.toFloat(), it) }
                        } catch (e: NumberFormatException) {
                            null // Skip invalid entries
                        }
                    }.filterNotNull() ?: emptyList()
                    _chartData.postValue(entries)
                } else {
                    _error.postValue(response.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            }
        }
    }

    fun getUserToken(): Flow<UserModel> {
        return userRepository.getSession()
    }
}