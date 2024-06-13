package com.capstone.have.data.repository

import com.capstone.have.data.response.FoodRecommendationsItem
import com.capstone.have.data.retrofit.ApiService

class CalorieRepository(private val apiService: ApiService) {

    suspend fun getFoodRecommendations(token: String): List<FoodRecommendationsItem?> {
        val response = apiService.getFoodRecommendation("Bearer $token")
        return response.data?.foodRecommendations ?: emptyList()
    }
}
