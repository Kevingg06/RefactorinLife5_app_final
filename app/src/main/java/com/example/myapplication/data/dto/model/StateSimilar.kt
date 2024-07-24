package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductsResponse

sealed class StateSimilar {
    data class Success(val info: ProductsResponse): StateSimilar()
    data class Error(val error: String): StateSimilar()
    data object Loading : StateSimilar()
}
