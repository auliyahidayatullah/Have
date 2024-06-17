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

	@field:SerializedName("calories")
	val calories: List<CaloriesItem?> = emptyList()
)

data class CaloriesItem(

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("calorieId")
	val calorieId: String? = null,

	@field:SerializedName("foodname")
	val foodname: String? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null
)
