package com.capstone.have

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import org.json.JSONObject
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.BufferedReader
import java.io.InputStreamReader

class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 1,
    val modelName: String = "foods_model_with_metadata.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {

    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }.copy(Bitmap.Config.ARGB_8888, true)

        Log.d(TAG, "Original Bitmap dimensions: ${bitmap.width} x ${bitmap.height}")

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(128, 128, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .build()

        val tensorImage = TensorImage.fromBitmap(bitmap)
        val processedImage = imageProcessor.process(tensorImage)

        Log.d(TAG, "Processed Image dimensions: ${processedImage.width}, ${processedImage.height}")

        val results = imageClassifier?.classify(processedImage)

        classifierListener?.onResults(results, 0)
    }

    fun getLabelFromJson(label: String): String {
        val jsonFileName = "class_indices.json"
        val jsonFile = context.assets.open(jsonFileName)
        val reader = BufferedReader(InputStreamReader(jsonFile))
        val jsonString = reader.use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        return if (jsonObject.has(label)) {
            jsonObject.getString(label)
        } else {
            "Label not found"
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
            inferenceTime: Long
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
