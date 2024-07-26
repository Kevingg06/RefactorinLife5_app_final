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
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(TokenHolder.savedToken))
        .build()

    //http://localhost:3000
    //https://stoplight.io/mocks/tomasas214456/mock-rf5/461694548
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://stoplight.io/mocks/tomasas214456/mock-rf5/461694548")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<CommentService>()

    fun getComments(id : Int) : Response<CommentsResponse> {
        return service.getComments(id)
    }


}