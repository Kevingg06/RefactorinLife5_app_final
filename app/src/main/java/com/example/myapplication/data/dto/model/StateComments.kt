package com.example.myapplication.data.dto.model

import com.example.myapplication.data.dto.response.CommentsResponse

sealed class StateComments {
    data class Success(val info : CommentsResponse) : StateComments()

    data class Error(val message : String) : StateComments()

    data object Loading : StateComments()
}