package com.capstone.have.ui.menu.sleep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.SleepRepository
import com.capstone.have.data.response.AddSleepResponse
import kotlinx.coroutines.launch

class SleepViewModel(private val repository: SleepRepository) : ViewModel() {

    private val _addSleepResult = MutableLiveData<AddSleepResponse>()
    val addSleepResult: LiveData<AddSleepResponse> = _addSleepResult

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
}