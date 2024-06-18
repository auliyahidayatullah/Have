package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class BigCaloriesResponse(

    @field:SerializedName("data")
	val bigCaloriesData: List<BigCaloriesDataItem?> = emptyList(),

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: String? = null
)

data class BigCaloriesDataItem(

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("calorieId")
	val calorieId: String? = null,

	@field:SerializedName("foodname")
	val foodname: String? = null,

	@field:SerializedName("calories")
	val calories: String? = null
)
