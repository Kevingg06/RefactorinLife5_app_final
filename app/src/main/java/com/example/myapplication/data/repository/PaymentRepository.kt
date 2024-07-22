package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.model.PaymentResponse
import com.example.myapplication.data.service.PaymentService

class PaymentRepository(private val paymentService: PaymentService) {
    suspend fun getPaymentMethods(): PaymentResponse {
        return paymentService.getPaymentMethods()
    }
}