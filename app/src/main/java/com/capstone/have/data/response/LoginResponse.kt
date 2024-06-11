package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(
	@SerializedName("data")
	val data: Data? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: String? = null
)

data class Data(
	@SerializedName("token")
	val token: String? = null
)