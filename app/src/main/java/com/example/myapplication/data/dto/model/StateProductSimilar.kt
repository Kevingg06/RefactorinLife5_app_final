package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductsResponse

sealed class StateProductSimilar {
    data class Success(val info: ProductsResponse) : StateProductSimilar()
    data class Error (val message: String?): StateProductSimilar()
    data object Loading : StateProductSimilar()
}