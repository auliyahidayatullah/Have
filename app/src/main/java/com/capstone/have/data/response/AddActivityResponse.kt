package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class AddActivityResponse(

	@field:SerializedName("data")
	val data: AddActivity? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AddActivity(

	@field:SerializedName("activityId")
	val activityId: Int? = null
)
