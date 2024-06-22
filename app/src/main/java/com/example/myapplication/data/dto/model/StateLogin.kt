package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.LoginResponse

sealed class StateLogin {
    data class Success(val info: LoginResponse) : StateLogin()
    data class Error(val message: String) : StateLogin()
    data object Loading : StateLogin()
}