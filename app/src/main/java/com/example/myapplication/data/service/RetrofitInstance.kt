package com.example.myapplication.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api-payments-1ztc.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: PaymentService by lazy {
        retrofit.create(PaymentService::class.java)
    }
}