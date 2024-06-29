package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductTypesResponse

sealed class StateProductType {
    data class Success(val info: ProductTypesResponse) : StateProductType()
    data class Error(val message: String) : StateProductType()
    data object Loading : StateProductType()
}