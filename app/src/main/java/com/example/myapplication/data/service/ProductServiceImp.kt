package com.example.myapplication.data.service

import com.example.myapplication.data.dto.request.AuthInterceptor
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.data.utils.TokenHolder.savedToken
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class ProductServiceImp {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(savedToken))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Constants.PRODUCTS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<ProductService>()

    suspend fun getProductTypes(): Response<ProductTypesResponse> {
        return service.getProductTypes()
    }

    suspend fun getProducts(): Response<ProductsResponse> {
        return service.getProducts()
    }

    suspend fun getDailyOffer(): Response<SingleProductResponse> {
        return service.getDailyOffer()
    }

    suspend fun getSimilarProducts(id: Int): Response<ProductsResponse> {
        return service.getSimilarProducts(id)
    }

    suspend fun updateFavoriteProduct(productId: Int): Response<Unit> {
        return service.updateFavorite(productId)
    }
}
