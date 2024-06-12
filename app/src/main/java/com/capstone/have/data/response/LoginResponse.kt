package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class LoginData(
	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
