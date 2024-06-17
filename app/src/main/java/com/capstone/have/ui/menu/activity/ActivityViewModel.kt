package com.capstone.have.ui.menu.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.AllactivityItem
import kotlinx.coroutines.flow.Flow
import com.capstone.have.data.Result
import com.capstone.have.data.response.UpcomingActivityResponse
import com.capstone.have.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ActivityViewModel (private val userRepository: UserRepository, private val activityRepository: ActivityRepository) : ViewModel() {

    private val _activity = MutableLiveData<UpcomingActivityResponse>()
    val activity: LiveData<UpcomingActivityResponse> = _activity

    fun getUserToken(): Flow<UserModel> {
        return userRepository.getSession()
    }
    fun getYourActivity(): LiveData<Result<List<AllactivityItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = activityRepository.getActivity()
            if (response.status == "fail") {
                emit(Result.Error(response.message ?: "Unknown error"))
            } else {
                emit(Result.Success(response.data!!.allactivity.filterNotNull()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Network error"))
        }
    }

    fun getUpcomingActivity(token: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService(token).getUpcomingActivity()
                Log.d("API Response", response.toString())
                _activity.postValue(response)
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
                _activity.postValue(UpcomingActivityResponse(status = "fail", message = e.message))
            }
        }
    }
}