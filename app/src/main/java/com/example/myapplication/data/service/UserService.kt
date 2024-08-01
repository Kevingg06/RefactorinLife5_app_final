package com.example.myapplication.data.service

import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.dto.request.RegisterRequest
import com.example.myapplication.data.dto.response.LoginResponse
import com.example.myapplication.data.dto.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/auth/register")
    suspend fun register(@Body loginRequest: RegisterRequest): Response<RegisterResponse>
}
