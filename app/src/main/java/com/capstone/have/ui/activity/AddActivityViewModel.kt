package com.capstone.have.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.AddActivityResponse
import kotlinx.coroutines.launch

class AddActivityViewModel(private val repository: ActivityRepository) : ViewModel() {

    private val _addActivityResult = MutableLiveData<AddActivityResponse>()
    val addActivityResult: LiveData<AddActivityResponse> = _addActivityResult

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
}