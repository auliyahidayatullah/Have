package com.capstone.have.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.databinding.ActivityLandingBinding
import com.capstone.have.ui.login.LoginActivity
import com.capstone.have.ui.main.MainActivity
import com.capstone.have.ui.signup.SignUpActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(USER_PREFERENCE, MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean(LOGIN_STATUS, false)

        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            binding.btnSignIn.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            binding.btnSignUp.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
    }

    companion object {
        const val USER_PREFERENCE = "user_prefs"
        const val LOGIN_STATUS = "is_logged_in"

    }
}