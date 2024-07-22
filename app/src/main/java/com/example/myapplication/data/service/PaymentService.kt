package com.example.myapplication.data.service

import com.example.myapplication.data.dto.model.PaymentResponse
import com.example.myapplication.data.dto.response.PaymentMethodsResponse
import retrofit2.Response
import retrofit2.http.GET

interface PaymentService {
    @GET("/api/v1/payment-methods")
    suspend fun getPaymentMethods(): Response<PaymentMethodsResponse>
}