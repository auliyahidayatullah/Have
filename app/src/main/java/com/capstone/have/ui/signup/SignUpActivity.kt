package com.capstone.have.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.R
import com.capstone.have.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    private var isNameFilled = false
    private var isEmailFilled = false
    private var isPasswordFilled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener(this)

        validation()

    }

//    private fun observeViewModel() {
//        viewModel.registerResult.observe(this) { result ->
//            if (result.error == true) {
//                Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }
//    }
    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_signUp) {
            val name = binding.edSignupName.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()
            val username = binding.edSignupUsername.text.toString().trim()
            val password = binding.edSignupPassword.text.toString().trim()

            if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && username.isNotBlank()) {
//                viewModel.register(name, email, password)
            } else {
                Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun validation() {
        binding.edSignupName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                isNameFilled = s?.isNotBlank() ?: false
                checkFields()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                isNameFilled = s?.isNotBlank() ?: false
                checkFields()
            }

        })

        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                if (!isValidEmail(email)) {
                    binding.edEmail.error = EMAIL_ERROR
                } else {
                    binding.edEmail.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isEmailFilled = s?.isNotBlank() ?: false
                checkFields()
            }

        })
        binding.edSignupUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                isNameFilled = s?.isNotBlank() ?: false
                checkFields()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                isNameFilled = s?.isNotBlank() ?: false
                checkFields()
            }

        })

        binding.edSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if (password.length < 8) {
                    binding.edSignupPassword.error = PASSWORD_ERROR
                } else {
                    binding.edSignupPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isPasswordFilled = s?.isNotBlank() ?: false
                checkFields()
            }
        })

    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkFields() {
        binding.btnSignUp.isEnabled = isNameFilled && isEmailFilled && isPasswordFilled
    }

    companion object{
        const val PASSWORD_ERROR = "Password harus memiliki setidaknya 8 karakter"
        const val EMAIL_ERROR = "Email tidak valid"
    }
}