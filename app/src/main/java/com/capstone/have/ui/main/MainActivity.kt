package com.capstone.have.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.have.R
import com.capstone.have.databinding.ActivityMainBinding
import com.capstone.have.ui.LandingActivity
import com.capstone.have.ui.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        SETUP MENU BAR
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_sleep, R.id.navigation_activity, R.id.navigation_calorie
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        SETUP SAVE SESSION
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LandingActivity::class.java))
                finish()
            } else {
            }
        }
    }

//    SETUP LOGOUT
    private fun logout (){
        val sharedPreferences = getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

//    SETUP BACK TO APP LAUNCHER
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    companion object {
        const val USER_PREFERENCE = "user_prefs"
    }

}
