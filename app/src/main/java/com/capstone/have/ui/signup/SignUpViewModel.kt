package com.capstone.have.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.RegisterResponse
import kotlinx.coroutines.launch

class SignUpViewModel (private val repository: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    fun register(name: String, username: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(name,email, username, password)
                _registerResult.postValue(response)
            } catch (e: Exception) {
                _registerResult.postValue(RegisterResponse(status = "failed", message = e.message))
            }
        }
    }
}