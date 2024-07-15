package com.example.myapplication.data.dto.response

import com.example.myapplication.data.dto.model.Image
import com.google.gson.annotations.SerializedName

data class SingleProductResponse(
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
    @SerializedName("isFavorite")
    val isFavorite: Boolean?,
    @SerializedName("dailyOffer")
    val dailyOffer : Boolean?
)

data class ProductType2 (
    @SerializedName("idProductType")
    val idProductType: Int?,
    @SerializedName("descripcion")
    val descripcion: String?
)
