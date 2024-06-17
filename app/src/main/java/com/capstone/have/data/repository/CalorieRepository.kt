package com.capstone.have.data.repository

import com.capstone.have.data.response.AddCalorieResponse
import com.capstone.have.data.response.ExerciseResponse
import com.capstone.have.data.response.FoodRecommendationsItem
import com.capstone.have.data.response.FoodsRecommendationResponse
import com.capstone.have.data.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CalorieRepository(private val apiService: ApiService) {

    suspend fun getFoodRecommendations():FoodsRecommendationResponse {
        return apiService.getFoodRecommendation()
    }

    suspend fun addCalories(file: MultipartBody.Part, foodName: RequestBody, calories : RequestBody): AddCalorieResponse {
        return apiService.addCalories(file, foodName, calories)
    }

    companion object {
        fun getInstance(apiService: ApiService) = CalorieRepository(apiService)
    }
}
