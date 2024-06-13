package com.capstone.have.ui.activity

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.have.R
import com.capstone.have.databinding.ActivityAddBinding
import com.capstone.have.ui.ViewModelFactory
import com.capstone.have.ui.main.MainActivity
import java.util.Calendar
import java.util.Locale

class AddActivity : AppCompatActivity(), View.OnClickListener {
    private val addActivityViewModel by viewModels<AddActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edTime.setOnClickListener {
            showTimePickerDialog()
        }
        binding.addButton.setOnClickListener(this)
        observeViewModel()
    }

    private fun observeViewModel() {
        addActivityViewModel.addActivityResult.observe(this) { result ->
            if (result.status == "failed") {
                showLoading(false)
                Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
            } else {
                showLoading(true)
                showPopupDialog()
            }
        }
    }

    //    SETUP ONCLICK BTN ADD
    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_signUp) {
            val activity = binding.edAddActivity.text.toString().trim()
            val time = binding.edTime.text.toString().trim()

            if (activity.isNotBlank() && time.isNotBlank()) {
                addActivityViewModel.addActivity(activity, time)
            } else {
                Toast.makeText(this, "Activity is must filled!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //    SETUP TIME PICKER
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val selectedTime =
                    String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                binding.edTime.setText(selectedTime)
            }, hour, minute, true
        )

        timePickerDialog.show()
    }

    //    SETUP POPUP DIALOG
    private fun showPopupDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Activity Added Successfully!")
        builder.setMessage("Your activity was successfully added.\\nDo you want to add another activity?\\n(We recommend you to add at least 3 activities.)")
        builder.setPositiveButton("Yes") { _, _ ->
            startActivity(Intent(this, AddActivity::class.java))
        }
        builder.setNegativeButton("No") { _, _ ->
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        builder.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}