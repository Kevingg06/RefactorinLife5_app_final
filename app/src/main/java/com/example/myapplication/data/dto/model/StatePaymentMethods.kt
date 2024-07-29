package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.PaymentMethod

sealed class StatePaymentMethods {
    data class Success(val info: List<PaymentMethod>) : StatePaymentMethods()
    data class Error(val message: String) : StatePaymentMethods()
    data object Loading : StatePaymentMethods()
}
