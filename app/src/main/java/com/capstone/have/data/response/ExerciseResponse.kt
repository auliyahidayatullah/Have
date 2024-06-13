package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class ExerciseResponse(

	@field:SerializedName("data")
	val data: ExerciseData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ExerciseRecommendationsItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("calories")
	val calories: String? = null
)

data class ExerciseData(

	@field:SerializedName("exerciseRecommendations")
	val exerciseRecommendations: List<ExerciseRecommendationsItem?> = emptyList()
)
