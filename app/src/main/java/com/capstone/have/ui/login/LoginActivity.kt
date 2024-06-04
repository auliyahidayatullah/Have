package com.capstone.have.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

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

//            viewModel.login(email, password)
        }
    }

//    private fun observeViewModel() {
//        viewModel.loginResult.observe(this) { result ->
//            if (result.error == true) {
//                Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
//            } else {
//                result.loginResult?.let { loginResult ->
//                    val name = loginResult.name ?: ""
//                    val token = loginResult.token ?: ""
//                    viewModel.saveSession(UserModel(name, token))
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                }
//            }
//        }
//    }
}