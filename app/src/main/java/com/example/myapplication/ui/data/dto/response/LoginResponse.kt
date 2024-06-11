package com.example.myapplication.ui.data.dto.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("jwt")
    val jwt: String?
)