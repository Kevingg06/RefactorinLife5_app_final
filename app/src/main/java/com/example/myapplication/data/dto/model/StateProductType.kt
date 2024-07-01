package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductTypesResponse

sealed class StateProduct {
    data class SuccessProductType(val info: ProductTypesResponse) : StateProduct()
    data class SuccessProducts(val info: ProductTypesResponse) : StateProduct()
    data class SuccessLastProduct(val info: ProductTypesResponse) : StateProduct()
    data class SuccessDailyOffer(val info: ProductTypesResponse) : StateProduct()
    data class Error(val message: String) : StateProduct()
    data object Loading : StateProduct()
}