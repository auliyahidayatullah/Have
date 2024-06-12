package com.capstone.have.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.UserRepository
class MainViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

}