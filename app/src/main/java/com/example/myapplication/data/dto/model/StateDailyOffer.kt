package com.example.myapplication.data.dto.model

sealed class StateDailyOffer {
    data object Success : StateDailyOffer()
    data class Error(val message: String?) : StateDailyOffer()
    data object Loading: StateDailyOffer()
}