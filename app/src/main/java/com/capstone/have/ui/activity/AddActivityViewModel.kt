package com.capstone.have.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.response.AddActivityResponse
import kotlinx.coroutines.launch
import kotlin.time.Duration

class AddActivityViewModel(private val repository: ActivityRepository) : ViewModel() {

    /*private val _addActivityResult = MutableLiveData<Result<AddActivityResponse>>()
    val addActivityResult: LiveData<Result<AddActivityResponse>>
        get() = _addActivityResult

    fun addActivity(name: String, duration: String) {
        viewModelScope.launch {
            val result = repository.addActivity(name, duration)
            _addActivityResult.value = result
        }
    }*/
}