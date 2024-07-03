package com.example.myapplication.data.service

import com.example.myapplication.data.dto.response.ProductResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("/mocks/refactoring-life/refactoring-life/431128877/api/v2/product-types")
    suspend fun getProductTypes(): Response<ProductTypesResponse>

    @GET("/mocks/refactoring-life/refactoring-life/431128877/api/v1/products")
    suspend fun getProducts(): Response<ProductResponse>

    @GET("/api/v1/products/lastUserProduct")
    suspend fun getLastUserProduct(): Response<SingleProductResponse>

    @GET("/api/v1/products/daily-offer")
    suspend fun getDailyOffer(): Response<SingleProductResponse>
}
