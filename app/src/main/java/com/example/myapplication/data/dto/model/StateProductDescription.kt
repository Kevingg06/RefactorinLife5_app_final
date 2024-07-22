package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductExact

sealed class StateProductDescription {
    data class SuccessProduct(val info: ProductExact) : StateProductDescription()
    data class Error(val message: String) : StateProductDescription()
    data object Loading : StateProductDescription()
}
