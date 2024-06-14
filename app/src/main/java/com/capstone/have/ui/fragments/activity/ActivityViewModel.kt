package com.capstone.have.ui.fragments.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.ActivityResponse
import com.capstone.have.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ActivityViewModel (private val userRepository: UserRepository) : ViewModel() {
    private val _activity = MutableLiveData<ActivityResponse>()
    val activity: LiveData<ActivityResponse> = _activity

    fun getUserToken(): Flow<UserModel> {
        return userRepository.getSession()
    }
    fun getActivity(activityId: String, token : String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService(token).getActivity(activityId)
                _activity.postValue(response)
            } catch (e: Exception) {
                _activity.postValue(ActivityResponse(status = "failed", message = e.message))
            }
        }
    }




}