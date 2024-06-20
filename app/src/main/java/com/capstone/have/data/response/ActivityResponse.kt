package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class ActivityResponse(

	@field:SerializedName("data")
	val data: ActivityData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AllactivityItem(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("activityId")
	val activityId: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class ActivityData(

	@field:SerializedName("allactivity")
	val allactivity: List<AllactivityItem?> = emptyList()
)
