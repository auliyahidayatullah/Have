package com.capstone.have.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.response.LoginResponse
import kotlinx.coroutines.launch

class SignInViewModel (private val repository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                _loginResult.postValue(response)
            } catch (e: Exception) {
                _loginResult.postValue(LoginResponse(status = "failed", message = e.message))
            }
        }
    }
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}