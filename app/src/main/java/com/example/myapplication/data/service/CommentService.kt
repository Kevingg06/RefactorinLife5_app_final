package com.example.myapplication.data.service

import com.example.myapplication.data.dto.response.CommentsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentService {
    @GET("/api/v1/comments")
    suspend fun getComments(@Query("idProduct") productId: Int): Response<CommentsResponse>

}