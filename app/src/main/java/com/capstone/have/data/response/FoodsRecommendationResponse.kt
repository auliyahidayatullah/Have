package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class FoodsRecommendationResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("foodRecommendations")
	val foodRecommendations: List<FoodRecommendationsItem?>? = null
)

data class FoodRecommendationsItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("calories")
	val calories: String? = null
)
