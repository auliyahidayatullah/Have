package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class CalorieOverviewResponse(

	@field:SerializedName("data")
	val data: CalorieOverviewData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CalorieOverviewData(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("calorieId")
	val calorieId: String? = null,

	@field:SerializedName("foodname")
	val foodname: String? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("calories")
	val calories: String? = null
)
