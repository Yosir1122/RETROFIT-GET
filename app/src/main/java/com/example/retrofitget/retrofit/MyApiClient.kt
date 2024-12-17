package com.example.retrofitget.retrofit

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyApiClient {
    const val BASE_URL="https://todoeasy.pythonanywhere.com/"

    fun retrofitService(context: Context):RetrofitService{
        return getRetrofit(context).create(RetrofitService::class.java)
    }
    private fun getRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}