package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.response.CommentsResponse
import com.example.myapplication.data.service.CommentsServiceImp
import retrofit2.Response

class CommentsRepository(private val service : CommentsServiceImp = CommentsServiceImp()) {

    fun getComments(id : Int) : Response<CommentsResponse> {
        return service.getComments(id)
    }
}