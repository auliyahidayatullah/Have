package com.capstone.have.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.ActivityResponse
import com.capstone.have.data.response.AddActivityResponse
import com.capstone.have.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository, private val userRepository: UserRepository) : ViewModel() {

    private val _addActivityResult = MutableLiveData<AddActivityResponse>()
    val addActivityResult: LiveData<AddActivityResponse> = _addActivityResult
    private val _activity = MutableLiveData<ActivityResponse>()
    val activity: LiveData<ActivityResponse> = _activity

    fun addActivity(name: String, duration: String) {
        viewModelScope.launch {
            try {
                val response = repository.addActivity(name,duration)
                _addActivityResult.postValue(response)
            } catch (e: Exception) {
                _addActivityResult.postValue(AddActivityResponse(status = "failed", message = e.message))
            }
        }
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

    fun getUserToken(): Flow<UserModel> {
        return userRepository.getSession()
    }
}