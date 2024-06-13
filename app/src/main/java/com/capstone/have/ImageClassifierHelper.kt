package com.capstone.have

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.IOException

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
        } catch (e: IOException) {
            classifierListener?.onError("Failed to load model: ${e.message}")
            Log.e(TAG, "Model loading error: ${e.message}", e)
        } catch (e: IllegalStateException) {
            classifierListener?.onError("Image classifier initialization failed: ${e.message}")
            Log.e(TAG, "Initialization error: ${e.message}", e)
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        try {
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
        } catch (e: IOException) {
            classifierListener?.onError("Image classification failed: ${e.message}")
            Log.e(TAG, "Image classification error: ${e.message}", e)
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Classifications>?, inferenceTime: Long)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
