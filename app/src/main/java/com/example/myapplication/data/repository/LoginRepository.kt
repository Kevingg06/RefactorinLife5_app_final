package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.dto.response.LoginResponse
import com.example.myapplication.data.service.LoginService
import retrofit2.Response

class LoginRepository(private val service: LoginService = LoginService()) {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return service.login(loginRequest)
    }
}