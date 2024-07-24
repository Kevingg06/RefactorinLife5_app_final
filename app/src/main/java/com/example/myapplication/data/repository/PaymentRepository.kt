package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.response.PaymentMethodsResponse
import com.example.myapplication.data.service.PaymentServiceImp
import retrofit2.Response

class PaymentRepository(private val service: PaymentServiceImp = PaymentServiceImp()) {

    suspend fun getPaymentMethods(): Response<PaymentMethodsResponse> {
        return service.getPaymentMethods()
    }
}
