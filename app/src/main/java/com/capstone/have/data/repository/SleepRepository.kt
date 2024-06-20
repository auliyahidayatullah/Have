package com.capstone.have.data.repository

import com.capstone.have.data.response.AddSleepResponse
import com.capstone.have.data.retrofit.ApiService

class SleepRepository(private val apiService: ApiService) {

    suspend fun addSleep(bedtime: String, wakeuptime: String): AddSleepResponse {
        return apiService.addSleep(bedtime, wakeuptime)
    }

    companion object{
        fun getInstance(apiService: ApiService) = SleepRepository(apiService)
    }
}