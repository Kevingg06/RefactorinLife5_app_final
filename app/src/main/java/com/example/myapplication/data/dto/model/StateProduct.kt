package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductResponse

sealed class StateProduct {
    data class Success(val info: ProductResponse) : StateProduct()
    data class Error(val message: String) : StateProduct()
    data object Loading : StateProduct()
}