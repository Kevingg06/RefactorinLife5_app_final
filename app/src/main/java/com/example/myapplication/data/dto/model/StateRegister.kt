package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.RegisterResponse

sealed class StateRegister {
    data class Success(val info: RegisterResponse) : StateRegister()
    data class Error(val message: String) : StateRegister()
    data object Loading : StateRegister()
}
