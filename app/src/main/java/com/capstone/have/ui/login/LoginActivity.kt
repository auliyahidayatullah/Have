package com.capstone.have.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.data.preference.UserModel
import com.capstone.have.databinding.ActivityLoginBinding
import com.capstone.have.ui.main.MainActivity
import com.capstone.have.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignInViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            val email = binding.edSigninUsername.text.toString().trim()
            val password = binding.edSigninPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { result ->
            if (result.status == "fail") {
                Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
            } else {
                result.data?.let { loginResult ->
                    val token = loginResult.token ?: ""
                    viewModel.saveSession(UserModel(token))
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}