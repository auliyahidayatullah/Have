package com.capstone.have.data.repository

import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.preference.UserPreference
import com.capstone.have.data.response.LoginResponse
import com.capstone.have.data.response.RegisterResponse
import com.capstone.have.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference, private val apiService: ApiService
) {

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun saveSession(userModel: UserModel) {
        userPreference.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun register(fullname :String, username: String, email: String, password: String): RegisterResponse {
        return apiService.register(fullname, username, email, password)
    }


    companion object {
        //        @Volatile
//        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference) = UserRepository(userPreference, apiService)
    }
}