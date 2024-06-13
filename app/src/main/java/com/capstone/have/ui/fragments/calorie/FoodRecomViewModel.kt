package com.capstone.have.ui.fragments.calorie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.CalorieRepository
import com.capstone.have.data.response.FoodRecommendationsItem
import kotlinx.coroutines.launch

class FoodRecomViewModel(private val repository: CalorieRepository) : ViewModel(),
    ViewModelProvider.Factory {

    private val _foodRecommendations = MutableLiveData<List<FoodRecommendationsItem>>()
    val foodRecommendations: LiveData<List<FoodRecommendationsItem>> = _foodRecommendations

    fun getFoodRecommendations(token: String) {
        viewModelScope.launch {
            try {
                val foods = repository.getFoodRecommendations(token)
                    .filterNotNull()
                _foodRecommendations.value = foods
            } catch (e: Exception) {
                _foodRecommendations.value = emptyList()
            }
        }
    }
}
