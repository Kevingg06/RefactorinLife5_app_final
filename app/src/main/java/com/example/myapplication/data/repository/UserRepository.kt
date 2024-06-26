package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.dto.request.RegisterRequest
import com.example.myapplication.data.dto.response.LoginResponse
import com.example.myapplication.data.dto.response.RegisterResponse
import com.example.myapplication.data.service.UserServiceImp
import retrofit2.Response

class UserRepository(private val service: UserServiceImp = UserServiceImp()) {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return service.login(loginRequest)
    }

    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return service.register(registerRequest)
    }
}