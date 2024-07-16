package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse

sealed class StateProduct {
    data class SuccessProductType(val info: ProductTypesResponse) : StateProduct()
    data class SuccessProducts(val info: ProductsResponse) : StateProduct()
    data class SuccessLastUserProduct(val info: SingleProductResponse) : StateProduct()
    data class SuccessDailyOffer(val info: SingleProductResponse) : StateProduct()
    data class Error(val message: String) : StateProduct()
    data object Loading : StateProduct()
    data class FilteredProducts(var products: MutableList<Product>) : StateProduct()
    data object SuccessFavorites : StateProduct()
}
