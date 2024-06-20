package com.capstone.have.ui.menu.calorie

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.capstone.have.ImageClassifierHelper
import com.capstone.have.R
import com.capstone.have.databinding.FragmentCalorieBinding
import com.capstone.have.ui.ViewModelFactory
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.IOException

class CalorieFragment : Fragment() {

    private var _binding: FragmentCalorieBinding? = null
    private val binding get() = _binding!!
    private lateinit var calorieViewModel: CalorieViewModel

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { uri ->
                handleImageUri(uri)
            }
        } else {
            Toast.makeText(requireContext(), "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
        }
    }
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
            .replace(R.id.top_calorie_container, BigCaloriesFragment())
            .replace(R.id.food_rec_container, FoodRecomFragment())
            .commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        SETUP CHART
        val factory = ViewModelFactory.getInstance(requireContext())
        calorieViewModel = ViewModelProvider(this, factory)[CalorieViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            calorieViewModel.getUserToken().collect { userModel ->
                calorieViewModel.getCalorieData(userModel.token)
            }
        }

        calorieViewModel.chartData.observe(viewLifecycleOwner) { entries ->
            updateChart(entries)
        }

        calorieViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            showError(errorMessage)
        }

//        SETUP MODEL ML
        classIndices = loadClassIndices()
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    Log.e("CalorieFragment", "Classifier error: $error")
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let {
                        if (it.isNotEmpty()) {
                            val topResult = it[0].categories[0]
                            val predictedLabel = topResult.label.trim().lowercase()

                            Log.d("CalorieFragment", "Predicted food: $predictedLabel")
                            Log.d("CalorieFragment", "Predicted index: ${topResult.index}")

                            // Find the correct label based on the index
                            val predictedFood = classIndices.entries.firstOrNull { it.value == topResult.index.toString() }?.key
                            Log.d("CalorieFragment", "Predicted food item from index: $predictedFood")

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
                                Toast.makeText(requireContext(), "Invalid prediction index for ${topResult.index}", Toast.LENGTH_SHORT).show()
                                Log.e("CalorieFragment", "Invalid prediction index for ${topResult.index}")
                            }
                        } else {
                            Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show()
                            Log.e("CalorieFragment", "No results found")
                        }
                    } ?: run {
                        Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show()
                        Log.e("CalorieFragment", "No results found")
                    }
                }
            }
        )

//        SETUP FLOAT BTN
        binding.extendedFab.visibility = View.VISIBLE
        binding.extendedFab.setOnClickListener {
            showPictureDialog()
        }

        setupGalleryLauncher()
        setupFileLauncher()
    }

    private fun updateChart(entries: List<Entry>) {
        binding.errorMessage.visibility = View.GONE
        binding.calorieChart.visibility = View.VISIBLE

        val lineDataSet = LineDataSet(entries, "Calories").apply {
            lineWidth = 3f
        }
        val lineData = LineData(lineDataSet)
        binding.calorieChart.data = lineData

//        SET LIMIT LINE
        val limitLine = LimitLine(1500f, "Targets")
        limitLine.lineWidth = 2f
        limitLine.enableDashedLine(10f, 10f, 0f)
        limitLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        limitLine.textSize = 10f

        val yAxis = binding.calorieChart.axisLeft
        yAxis.addLimitLine(limitLine)

        binding.calorieChart.invalidate() // refresh chart
    }

    private fun showError(message: String) {
        binding.calorieChart.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = message
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
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private fun openGallery() {
        galleryLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
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
                Log.d("CalorieFragment", "Loaded class indices: $classIndicesMap")
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

    private val caloriesDict: Map<String, Int> = mapOf(
        "egg" to 92,
        "orange juice" to 45,
        "pancake" to 277,
        "rice" to 130,
        "sweet tea" to 260,
        "waffle" to 291
    )
}
