package com.example.myapplication.data.service

import com.example.myapplication.data.dto.request.AuthInterceptor
import com.example.myapplication.data.dto.response.PaymentMethodsResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.data.utils.TokenHolder.savedToken
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PaymentServiceImp {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(savedToken))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Constants.PAYMENTS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PaymentService::class.java)

    suspend fun getPaymentMethods(): Response<PaymentMethodsResponse> {
        return service.getPaymentMethods()
    }

}
