package com.example.myapplication.data.dto.response

import com.example.myapplication.data.dto.model.Image
import com.google.gson.annotations.SerializedName

data class ProductByIdResponse (
        @SerializedName("idProduct")
        val idProduct: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("productType")
        val productType: ProductType2?,
        @SerializedName("currency")
        val currency: String?,
        @SerializedName("price")
        val price: Double?,
        @SerializedName("images")
        val images: List<Image>?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("largeDescription")
        val largeDescription: String?,
        @SerializedName("isFavorite")
        var isFavorite: Boolean?
    )
