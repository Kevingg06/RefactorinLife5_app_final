package com.example.myapplication.data.service

import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.dto.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}