package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.ProductsSearchResponse

sealed class StateProductsSearch {
    data class Success(val info: ProductsSearchResponse) : StateProductsSearch()
    data object NoContent : StateProductsSearch()
    data class Error (val message: String?): StateProductsSearch()
    data object Loading : StateProductsSearch()
}