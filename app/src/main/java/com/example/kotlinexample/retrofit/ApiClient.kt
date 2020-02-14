package com.example.kotlinexample.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val BASE_URL : String = "https://my-json-server.typicode.com/santoshgupta183/myRepo/"
    private  fun getClient() : Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return client
    }

    val apiInterface = getClient().create(ApiInterface :: class.java)

    fun getInterface() : ApiInterface {
        return getClient().create(ApiInterface :: class.java)
    }
}