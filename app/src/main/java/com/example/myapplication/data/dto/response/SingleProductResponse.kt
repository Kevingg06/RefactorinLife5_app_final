package com.example.myapplication.data.dto.response

import com.google.gson.annotations.SerializedName

data class SingleProductResponse(
    @SerializedName("idProduct")
    val idProduct: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("productType")
    val productType: ProductType?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isFavorite")
    val isFavorite: String?,
    @SerializedName("description")
    val description: String?
)
