package com.example.myapplication.data.service

import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("/api/v1/product-types")
    suspend fun getProductTypes(): Response<ProductTypesResponse>

    @GET("/api/v1/products")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("/api/v1/products/daily-offer")
    suspend fun getDailyOffer(): Response<SingleProductResponse>

    @PUT("/api/v1/products/{idProduct}/favorite")
    suspend fun updateFavorite(@Path("idProduct") id: Int): Response<Unit>
}
