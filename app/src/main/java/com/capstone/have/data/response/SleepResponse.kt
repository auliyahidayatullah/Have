package com.capstone.have.data.response

data class SleepResponse(
	val data: List<DataItem?> = emptyList(),
	val message: String? = null,
	val status: String? = null
)

data class DataItem(
	val wakeuptime: String? = null,
	val updatedAt: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val bedtime: String? = null,
	val quality: String? = null
)

