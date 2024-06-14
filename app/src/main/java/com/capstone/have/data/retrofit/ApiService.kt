package com.capstone.have.data.retrofit

import com.capstone.have.data.response.ActivityResponse
import com.capstone.have.data.response.AddActivityResponse
import com.capstone.have.data.response.ExerciseRecommendationsItem
import com.capstone.have.data.response.ExerciseResponse
import com.capstone.have.data.response.FoodRecommendationsItem
import com.capstone.have.data.response.FoodsRecommendationResponse
import com.capstone.have.data.response.LoginResponse
import com.capstone.have.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("activity")
    suspend fun addActivity(
        @Field("name") name: String,
        @Field("duration") duration: String
    ): AddActivityResponse

    @GET("recommendations/exercise")
    suspend fun getExercise(
    ): ExerciseResponse

    @GET("activity/{id}")
    suspend fun getActivity(
        @Path("id") activityId: String
    ): ActivityResponse

    @GET("recommendations/food")
    suspend fun getFoodRecommendation(
    ): FoodsRecommendationResponse
}