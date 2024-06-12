package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class CalorieResponse(

	@field:SerializedName("data")
	val data: CalorieData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CalorieData(

	@field:SerializedName("calorieId")
	val calorieId: String? = null,

	@field:SerializedName("foodname")
	val foodname: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null
)
