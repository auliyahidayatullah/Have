package com.capstone.have.ui.fragments.calorie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.have.data.repository.CalorieRepository
import com.capstone.have.data.response.FoodRecommendationsItem
import kotlinx.coroutines.launch

class FoodRecomViewModel(private val repository: CalorieRepository) : ViewModel() {

    private val _foodRecommendations = MutableLiveData<List<FoodRecommendationsItem>>()
    val foodRecommendations: LiveData<List<FoodRecommendationsItem>> = _foodRecommendations

    fun getFoodRecommendations(token: String?) {
        token?.let {
            viewModelScope.launch {
                try {
                    val foods = repository.getFoodRecommendations(it)
                    _foodRecommendations.value = foods.filterNotNull()
                } catch (e: Exception) {
                    // Handle error case, e.g., show error message
                    _foodRecommendations.value = emptyList()
                }
            }
        }
    }
}
