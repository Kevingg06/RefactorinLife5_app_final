package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.service.ProductServiceImp
import retrofit2.Response

class ProductRepository(private val service: ProductServiceImp = ProductServiceImp()) {
    suspend fun getProductTypes(): Response<ProductTypesResponse> {
        return service.getProductTypes()
    }
}