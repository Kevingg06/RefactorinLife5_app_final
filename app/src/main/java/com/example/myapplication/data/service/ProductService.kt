package com.example.myapplication.data.service

import com.example.myapplication.data.dto.response.ProductByIdResponse
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.ProductsSearchResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("/api/v1/product-types")
    suspend fun getProductTypes(): Response<ProductTypesResponse>

    @GET("/api/v1/products")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("/api/v1/products")
    suspend fun searchProducts(
        @Query("idProductType") idProductType: Int,
        @Query("productName") productName: String,
        @Query("onlyFavorite") onlyFavorite: Boolean
    ): Response<ProductsSearchResponse>

    @GET("/api/v1/products/daily-offer")
    suspend fun getDailyOffer(): Response<SingleProductResponse>

    @GET("/api/v1/products/{idProduct}")
    suspend fun getProductById(@Path("idProduct") id: Int): Response<ProductByIdResponse>

    @GET("/api/v1/products/{idProduct}/similar")
    suspend fun getSimilarProductsById(
        @Path("idProduct") id: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ProductsResponse>

    @PUT("/api/v1/products/{idProduct}/favorite")
    suspend fun updateFavorite(@Path("idProduct") id: Int): Response<ProductByIdResponse>
}
