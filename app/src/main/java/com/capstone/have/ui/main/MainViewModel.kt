package com.capstone.have.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.capstone.have.data.preference.UserModel
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.response.ExerciseRecommendationsItem
import com.capstone.have.data.Result

class MainViewModel (private val userRepository: UserRepository, private val activityRepository: ActivityRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
    fun getListExercise(): LiveData<Result<List<ExerciseRecommendationsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = activityRepository.getExercise()
            if (response.status == "fail") {
                emit(Result.Error(response.message ?: "Unknown error"))
            } else {
                emit(Result.Success(response.data!!.exerciseRecommendations.filterNotNull()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Network error"))
        }
    }

}