package com.example.myapplication.data.service

import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class ProductServiceImp {

    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Constants.PRODUCTS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<ProductService>()

    suspend fun getProductTypes(token: String): Response<ProductTypesResponse> {
        return service.getProductTypes(token)
    }

    suspend fun getProducts(token: String): Response<ProductsResponse> {
        return service.getProducts(token)
    }

    suspend fun getDailyOffer(token: String): Response<SingleProductResponse> {
        return service.getDailyOffer(token)
    }

    suspend fun updateFavoriteProduct(token: String, productId: Int): Response<Unit>{
        return service.updateFavorite(token, productId)
    }
}
