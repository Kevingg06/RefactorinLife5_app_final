package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.LoginResponse

open class StateLoginViewModel {
    data class Success( val info: LoginResponse) : StateLoginViewModel()
    data class Error( val message: String) : StateLoginViewModel()
    data object Loading : StateLoginViewModel()
}