package com.example.myapplication.data.service

import com.example.myapplication.data.dto.request.AuthInterceptor
import com.example.myapplication.data.dto.response.CommentsResponse
import com.example.myapplication.data.utils.TokenHolder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class CommentsServiceImp {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(TokenHolder.savedToken))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api-comments-3yzc.onrender.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<CommentService>()

    suspend fun getComments(id: Int): Response<CommentsResponse> {
        return service.getComments(id)
    }


}