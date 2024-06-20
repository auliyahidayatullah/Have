package com.capstone.have.data.response

import com.google.gson.annotations.SerializedName

data class FoodsRecommendationResponse(

	@field:SerializedName("data")
	val data: FoodRecomData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class FoodRecomData(

	@field:SerializedName("foodRecommendations")
	val foodRecommendations: List<FoodRecommendationsItem?> = emptyList()
)

data class FoodRecommendationsItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("calories")
	val calories: String? = null
)
