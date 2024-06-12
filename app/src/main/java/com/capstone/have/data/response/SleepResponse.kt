package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class SleepResponse(

	@field:SerializedName("data")
	val data: SleepData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SleepData(

	@field:SerializedName("wakeuptime")
	val wakeuptime: String? = null,

	@field:SerializedName("sleepId")
	val sleepId: Int? = null,

	@field:SerializedName("bedtime")
	val bedtime: String? = null
)
