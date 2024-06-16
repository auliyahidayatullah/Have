package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class UpcomingActivityResponse(

	@field:SerializedName("data")
	val data: UpcomingActivityData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class UpcomingActivityData(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("activityId")
	val activityId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
)
