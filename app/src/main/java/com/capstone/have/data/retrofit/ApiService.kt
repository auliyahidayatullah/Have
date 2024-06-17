package com.capstone.have.data.retrofit

import com.capstone.have.data.response.ActivityResponse
import com.capstone.have.data.response.AddActivityResponse
import com.capstone.have.data.response.AddCalorieResponse
import com.capstone.have.data.response.ExerciseResponse
import com.capstone.have.data.response.FoodsRecommendationResponse
import com.capstone.have.data.response.LoginResponse
import com.capstone.have.data.response.RegisterResponse
import com.capstone.have.data.response.AddSleepResponse
import com.capstone.have.data.response.BigCaloriesResponse
import com.capstone.have.data.response.CalorieResponse
import com.capstone.have.data.response.SleepDurationResponse
import com.capstone.have.data.response.UpcomingActivityResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("activity")
    suspend fun getActivity(
    ): ActivityResponse

    @GET("/activity/upcoming")
    suspend fun getUpcomingActivity(
    ): UpcomingActivityResponse

    @GET("recommendations/food")
    suspend fun getFoodRecommendation(
    ): FoodsRecommendationResponse

    @GET("calories")
    suspend fun getCalories(
    ): CalorieResponse

    @GET("/calories/top3")
    suspend fun getBigCalories(
    ): BigCaloriesResponse

    @Multipart
    @POST("calories")
    suspend fun addCalories(
        @Part file: MultipartBody.Part,
        @Part("foodname") foodName: RequestBody,
        @Part("calories") calories: RequestBody,
    ): AddCalorieResponse

    @FormUrlEncoded
    @POST("sleeps")
    suspend fun addSleep(
        @Field("bedtime") bedtime: String,
        @Field("wakeuptime") wakeuptime: String
    ): AddSleepResponse

    @GET("/sleeps/duration")
    suspend fun getSleepDuration(
    ): SleepDurationResponse
}