package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class AddCalorieResponse(

	@field:SerializedName("data")
	val data: AddCalorieData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AddCalorieData(

	@field:SerializedName("calorieId")
	val calorieId: String? = null
)
