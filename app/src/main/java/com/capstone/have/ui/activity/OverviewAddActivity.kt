package com.capstone.have.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.databinding.ActivityOverviewAddBinding

class OverviewAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOverviewAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOverviewAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}