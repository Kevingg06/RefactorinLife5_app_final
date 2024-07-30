package com.example.myapplication.data.dto.response

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?,
    @SerializedName("totalProducts")
    val totalProducts: Int?,
    @SerializedName("products")
    val products: MutableList<Product>?
)

data class Product (
    @SerializedName("idProduct")
    val idProduct: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("productType")
    val productType: ProductType?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("isFavorite")
    var isFavorite: Boolean?
)