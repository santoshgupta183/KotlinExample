package com.example.kotlinexample.retrofit

import com.example.kotlinexample.models.UserResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("db")
    fun getUserList() : Call<UserResponseModel>

    @GET("db")
    suspend fun getUserList2() : UserResponseModel
}