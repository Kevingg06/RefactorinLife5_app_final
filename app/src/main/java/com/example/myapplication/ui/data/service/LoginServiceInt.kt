package com.example.myapplication.ui.data.service

import com.example.myapplication.ui.data.dto.request.LoginRequest
import com.example.myapplication.ui.data.dto.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServiceInt {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}