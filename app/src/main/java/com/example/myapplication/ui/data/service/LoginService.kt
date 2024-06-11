package com.example.myapplication.ui.data.service

import com.example.myapplication.ui.data.dto.request.LoginRequest
import com.example.myapplication.ui.data.dto.response.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class LoginService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<LoginServiceInt>()

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return service.login(loginRequest)
    }
}