package com.example.myapplication.data.dto.model

data class PaymentResponse(
    val paymentMethods: List<PaymentMethod>
)

data class PaymentMethod(
    val idPaymentMethod: Int,
    val entity: String,
    val installments: List<Installment>
)

data class Installment(
    val quantity: Int,
    val interest: String
)