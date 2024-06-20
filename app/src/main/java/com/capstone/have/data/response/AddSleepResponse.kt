package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class AddSleepResponse(

	@field:SerializedName("data")
	val data: AddSleep? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AddSleep(

	@field:SerializedName("sleepId")
	val sleepId: Int? = null
)
