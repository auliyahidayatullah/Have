package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class SleepDurationResponse(

	@field:SerializedName("data")
	val data: SleepDurationData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SleepDurationData(

	@field:SerializedName("duration")
	val duration: Duration? = null,

	@field:SerializedName("sleepId")
	val sleepId: Int? = null
)

data class Duration(

	@field:SerializedName("hours")
	val hours: Int? = null,

	@field:SerializedName("minutes")
	val minutes: Int? = null
)
