package com.example.myapplication.data.dto.response

import com.google.gson.annotations.SerializedName

data class CommentsResponse(
    @SerializedName("comments")
    val comments : List<Comments>?
)

data class Comments (
    @SerializedName("idComment")
    val idComment : Int?,
    @SerializedName("comment")
    val comment : String?,
    @SerializedName("commentBy")
    val commentBy : String?
)
