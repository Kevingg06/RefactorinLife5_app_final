package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductByIdResponse

sealed class StateProductById {
    data class Success(val info: ProductByIdResponse) : StateProductById()
    data class Error (val message: String?): StateProductById()
    data object Loading : StateProductById()
}