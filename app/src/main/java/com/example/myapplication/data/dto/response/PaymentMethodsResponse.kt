package com.example.myapplication.data.dto.response

import com.google.gson.annotations.SerializedName

data class PaymentMethodsResponse(
    @SerializedName("paymentMethods")
    val paymentMethods: List<PaymentMethod>
)

data class PaymentMethod(
    @SerializedName("idPaymentMethod")
    val id: Int,
    @SerializedName("entity")
    val entity: String,
    @SerializedName("installments")
    val installments: List<Installment>
)

data class Installment(
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("interest")
    val interest: String
)
