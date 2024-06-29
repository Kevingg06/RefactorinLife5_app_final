package com.example.myapplication.data.service

import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ProductServiceImp {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_TEST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<ProductService>()

    suspend fun getProductTypes(): Response<ProductTypesResponse> {
        return service.getProductTypes()
    }
}