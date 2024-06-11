package com.capstone.have.data.retrofit

import android.content.Context
import com.capstone.have.data.preference.UserPreference
import com.capstone.have.data.preference.dataStore
import com.capstone.have.data.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(null)
        return UserRepository.getInstance(apiService, pref)
    }
}