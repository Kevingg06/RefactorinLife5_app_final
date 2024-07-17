package com.example.myapplication.data.service

import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.dto.request.RegisterRequest
import com.example.myapplication.data.dto.response.LoginResponse
import com.example.myapplication.data.dto.response.RegisterResponse
import com.example.myapplication.data.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class UserServiceImp {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.USER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<UserService>()

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return service.login(loginRequest)
    }

    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return service.register(registerRequest)
    }
}
