package com.capstone.have.ui.menu.calorie

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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.have.ImageClassifierHelper
import com.capstone.have.R
import com.capstone.have.databinding.FragmentCalorieBinding
import org.json.JSONException
import org.json.JSONObject
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.IOException

class CalorieFragment : Fragment() {

    private var _binding: FragmentCalorieBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var fileLauncher: ActivityResultLauncher<Intent>

    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private var currentImageUri: Uri? = null

    private lateinit var classIndices: Map<String, String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalorieBinding.inflate(inflater, container, false)
        val view = binding.root

        childFragmentManager.beginTransaction()
            .replace(R.id.food_rec_container, FoodRecomFragment())
            .commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        classIndices = loadClassIndices()

        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let {
                        if (it.isNotEmpty()) {
                            val topResult = it[0].categories[0]
                            val predictedLabel = topResult.label.trim().lowercase()

                            Log.d("CalorieFragment", "Predicted food: $predictedLabel")

                            val predictedIndex = classIndices[predictedLabel]

                            predictedIndex?.let { index ->
                                val predictedFood = index.toIntOrNull()?.let { classIndices.entries.firstOrNull { it.value == index }?.key }

                                predictedFood?.let {
                                    val calories = caloriesDict[it]
                                    if (calories != null) {
                                        currentImageUri?.let { uri ->
                                            val intent = Intent(requireContext(), AddFoodActivity::class.java).apply {
                                                putExtra(AddFoodActivity.EXTRA_FOOD_NAME, it)
                                                putExtra(AddFoodActivity.EXTRA_CALORIES, calories)
                                                putExtra(AddFoodActivity.EXTRA_IMAGE_URI, uri.toString())
                                            }
                                            startActivity(intent)
                                        }
                                    } else {
                                        Toast.makeText(requireContext(), "Informasi kalori untuk $it tidak tersedia", Toast.LENGTH_SHORT).show()
                                        Log.e("CalorieFragment", "Informasi kalori untuk $it tidak tersedia")
                                    }
                                } ?: run {
                                    Toast.makeText(requireContext(), "Invalid prediction index for $predictedLabel", Toast.LENGTH_SHORT).show()
                                    Log.e("CalorieFragment", "Invalid prediction index for $predictedLabel")
                                }
                            } ?: run {
                                Toast.makeText(requireContext(), "Invalid prediction label: $predictedLabel", Toast.LENGTH_SHORT).show()
                                Log.e("CalorieFragment", "Invalid prediction label: $predictedLabel")
                            }
                        } else {
                            Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        binding.extendedFab.visibility = View.VISIBLE
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

    private fun handleImageUri(imageUri: Uri) {
        currentImageUri = imageUri
        imageClassifierHelper.classifyStaticImage(imageUri)
    }

    private fun loadClassIndices(): Map<String, String> {
        val classIndicesMap = mutableMapOf<String, String>()

        try {
            requireContext().assets.open("class_indices.json").use { jsonInputStream ->
                val jsonString = jsonInputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(jsonString)

                val iterator = jsonObject.keys()
                while (iterator.hasNext()) {
                    val key = iterator.next()
                    val value = jsonObject.getString(key)
                    classIndicesMap[key] = value
                }
            }
        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Failed to load class indices", Toast.LENGTH_SHORT).show()
            Log.e("CalorieFragment", "Error loading class indices", e)
        } catch (e: JSONException) {
            Toast.makeText(requireContext(), "Failed to parse class indices JSON", Toast.LENGTH_SHORT).show()
            Log.e("CalorieFragment", "Error parsing class indices JSON", e)
        }

        return classIndicesMap
    }

    companion object {
        private val caloriesDict = mapOf(
            "egg" to "92 kalori",
            "orange juice" to "45 kalori",
            "pancake" to "277 kalori",
            "rice" to "130 kalori",
            "sweet tea" to "260 kalori",
            "waffle" to "291 kalori"
        )
    }
}
