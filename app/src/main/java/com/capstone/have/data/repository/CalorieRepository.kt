package com.capstone.have.data.repository

import com.capstone.have.data.response.ExerciseResponse
import com.capstone.have.data.response.FoodRecommendationsItem
import com.capstone.have.data.response.FoodsRecommendationResponse
import com.capstone.have.data.retrofit.ApiService

class CalorieRepository(private val apiService: ApiService) {

    suspend fun getFoodRecommendations():FoodsRecommendationResponse {
        return apiService.getFoodRecommendation()
    }

    companion object {
        fun getInstance(apiService: ApiService) = CalorieRepository(apiService)
    }
}
