package com.example.myapplication.data.service

import com.example.myapplication.data.dto.response.ProductTypesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("/mocks/refactoring-life/refactoring-life/431128877/api/v2/product-types")
    suspend fun getProductTypes(): Response<ProductTypesResponse>
}