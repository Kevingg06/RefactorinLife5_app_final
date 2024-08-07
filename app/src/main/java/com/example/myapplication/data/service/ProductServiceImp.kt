package com.example.myapplication.data.service

import com.example.myapplication.data.dto.request.AuthInterceptor
import com.example.myapplication.data.dto.response.ProductByIdResponse
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.ProductsSearchResponse
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

    suspend fun searchProducts(idProductType: Int,productName: String, onlyFavorite: Boolean): Response<ProductsSearchResponse> {
        return service.searchProducts(idProductType,productName,onlyFavorite)
    }

    suspend fun getDailyOffer(): Response<SingleProductResponse> {
        return service.getDailyOffer()
    }

    suspend fun getProductById(id: Int): Response<ProductByIdResponse> {
        return service.getProductById(id)
    }

    suspend fun updateFavoriteProduct(productId: Int): Response<ProductByIdResponse> {
        return service.updateFavorite(productId)
    }

    suspend fun getSimilarProductsById(id: Int, page: Int, size: Int): Response<ProductsResponse> {
        return service.getSimilarProductsById(id, page, size)
    }

    suspend fun updateDailyOffer(id: Int): Response<Unit>{
        return service.updateDailyOffer(id)
    }
}
