package com.capstone.have.data.repository

import com.capstone.have.data.response.AddActivityResponse
import com.capstone.have.data.retrofit.ApiService
import kotlin.time.Duration

class ActivityRepository(private val apiService: ApiService) {

    /*suspend fun addActivity(name: String, duration: String): Result<AddActivityResponse> {
        return try {
            val response = apiService.addActivity(name, duration)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }*/
}