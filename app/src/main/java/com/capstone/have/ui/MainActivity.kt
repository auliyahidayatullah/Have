package com.capstone.have.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.have.R
import com.capstone.have.data.Result
import com.capstone.have.databinding.ActivityMainBinding
import com.capstone.have.ui.LandingActivity.Companion.USER_PREFERENCE
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        try {
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navView.setupWithNavController(navController)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting up NavController", e)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, LandingActivity::class.java))
//                finish()
//            } else {
//                viewModel.getStories().observe(this) { result ->
//                    when (result) {
//                        is Result.Loading -> showLoading(true)
//                        is Result.Success -> {
//                            showLoading(false)
//                            setUserData(result.data)
//                        }
//                        is Result.Error -> {
//                            showLoading(false)
//                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    /*private fun logout (){
        val sharedPreferences = getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }*/

//    companion object {
//        const val USER_PREFERENCE = "user_prefs"
//    }

}
