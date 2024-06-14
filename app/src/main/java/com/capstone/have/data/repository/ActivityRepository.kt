package com.capstone.have.data.repository

import com.capstone.have.data.response.AddActivityResponse
import com.capstone.have.data.response.ExerciseRecommendationsItem
import com.capstone.have.data.response.ExerciseResponse
import com.capstone.have.data.retrofit.ApiService
import kotlin.time.Duration

class ActivityRepository(private val apiService: ApiService) {

    suspend fun addActivity(name: String, duration: String): AddActivityResponse {
       return apiService.addActivity(name, duration)
    }

    suspend fun getExercise(): ExerciseResponse {
        return apiService.getExercise()
    }

    companion object{
        fun getInstance(apiService: ApiService) = ActivityRepository(apiService)
    }
}