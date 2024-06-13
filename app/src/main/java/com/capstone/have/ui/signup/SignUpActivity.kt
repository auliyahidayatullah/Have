package com.capstone.have.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.R
import com.capstone.have.data.preference.UserModel
import com.capstone.have.databinding.ActivitySignUpBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.activity.OverviewAddActivity

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener(this)
        observeViewModel()
    }

    private fun observeViewModel() {
        signUpViewModel.registerResult.observe(this) { result ->
            if (result.status == "failed") {
                Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
            } else {
                result.data?.let {signUpResult ->
                    val token = signUpResult.token ?: ""
                    signUpViewModel.saveSession(UserModel(token))
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, OverviewAddActivity::class.java))
                    finish()
                }

            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_signUp) {
            val name = binding.edSignupName.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()
            val username = binding.edSignupUsername.text.toString().trim()
            val password = binding.edSignupPassword.text.toString().trim()

            if (name.isNotBlank() && email.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                signUpViewModel.register(name, email,username, password)

            } else {
                Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show()
            }

        }
    }
}