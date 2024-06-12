package com.capstone.have.ui.fragments.calorie

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.capstone.have.databinding.FragmentCalorieBinding
import com.capstone.have.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.Locale

class CalorieFragment : Fragment() {

    private lateinit var binding: FragmentCalorieBinding
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var fileLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalorieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.extendedFab.setOnClickListener {
            showPictureDialog()
        }

        setupCameraLauncher()
        setupGalleryLauncher()
        setupFileLauncher()
    }

    private fun setupCameraLauncher() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let { uri ->
                    handleImageUri(uri)
                }
            }
        }
    }

    private fun setupGalleryLauncher() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let { uri ->
                    handleImageUri(uri)
                }
            }
        }
    }

    private fun setupFileLauncher() {
        fileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let { uri ->
                    handleImageUri(uri)
                }
            }
        }
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Open Camera", "Select from Gallery", "Select from Files")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
                2 -> openFilePicker()
            }
        }
        pictureDialog.show()
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }
    }

    private fun requestCameraPermission() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }.launch(Manifest.permission.CAMERA)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun openFilePicker() {
        val fileIntent = Intent(Intent.ACTION_GET_CONTENT)
        fileIntent.type = "image/*"
        fileLauncher.launch(fileIntent)
    }

    private fun handleImageUri(uri: Uri) {
        val classifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let {
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            val result = it[0].categories[0]
                            val displayResult = "${result.label}: ${result.score}"
                            Log.d("CalorieFragment", "Raw classification result: $displayResult")
                            val calories = extractCaloriesFromResult(result.label, result.score)
                            Log.d("CalorieFragment", "Calories extracted: $calories")
                            moveToNextActivity(uri, calories)
                        } else {
                            showToast("No classification result available.")
                        }
                    } ?: showToast("No classification result available.")
                }
            }
        )
        classifierHelper.classifyStaticImage(uri)
    }

    private fun moveToNextActivity(imageUri: Uri, calories: String) {
        val intent = Intent(requireContext(), AddFoodActivity::class.java)
        intent.putExtra("imageUri", imageUri.toString())
        intent.putExtra("calories", calories)
        startActivity(intent)
    }

    private fun extractCaloriesFromResult(label: String, score: Float): String {
        // Log the result for debugging
        Log.d("CalorieFragment", "Classification result: $label")

        // Format the score to a string with 2 decimal places
        val formattedScore = String.format(Locale.getDefault(), "%.2f", score)


        return formattedScore
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
