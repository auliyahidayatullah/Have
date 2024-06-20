package com.capstone.have.data.retrofit

import android.content.Context
import com.capstone.have.data.preference.UserPreference
import com.capstone.have.data.preference.dataStore
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.repository.CalorieRepository
import com.capstone.have.data.repository.SleepRepository
import com.capstone.have.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(null)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideActivityRepository(context: Context): ActivityRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return ActivityRepository.getInstance(apiService)
    }

    fun provideCalorieRepository(context: Context): CalorieRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return CalorieRepository.getInstance(apiService)
    }
    fun provideSleepRepository(context: Context): SleepRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return SleepRepository.getInstance(apiService)
    }
}
