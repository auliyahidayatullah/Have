package com.capstone.have.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.have.data.repository.ActivityRepository
import com.capstone.have.data.repository.CalorieRepository
import com.capstone.have.data.repository.SleepRepository
import com.capstone.have.data.repository.UserRepository
import com.capstone.have.data.retrofit.Injection
import com.capstone.have.ui.activity.AddActivityViewModel
import com.capstone.have.ui.menu.activity.ActivityViewModel
import com.capstone.have.ui.menu.sleep.SleepViewModel
import com.capstone.have.ui.login.SignInViewModel
import com.capstone.have.ui.main.MainViewModel
import com.capstone.have.ui.menu.calorie.CalorieViewModel
import com.capstone.have.ui.signup.SignUpViewModel

class ViewModelFactory (
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository,
    private val calorieRepository: CalorieRepository,
    private val sleepRepository: SleepRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository, activityRepository, calorieRepository) as T
            }
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(AddActivityViewModel::class.java) -> {
                AddActivityViewModel(activityRepository) as T
            }
            modelClass.isAssignableFrom(ActivityViewModel::class.java) -> {
                ActivityViewModel(userRepository, activityRepository) as T
            }
            modelClass.isAssignableFrom(SleepViewModel::class.java) -> {
                SleepViewModel(userRepository,sleepRepository) as T
            }
            modelClass.isAssignableFrom(CalorieViewModel::class.java) -> {
                CalorieViewModel(calorieRepository, userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(
                Injection.provideUserRepository(context),
                Injection.provideActivityRepository(context),
                Injection.provideCalorieRepository(context),
                Injection.provideSleepRepository(context)
            )
        }
    }
}