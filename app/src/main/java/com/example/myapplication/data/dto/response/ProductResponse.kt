package com.example.myapplication.data.dto.response

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("size")
    val size: Int? = 0,
    @SerializedName("totalPages")
    val totalPages: Int? = 0,
    @SerializedName("totalProducts")
    val totalProducts: Int? = 0,
    @SerializedName("products")
    val products: MutableList<Product>? = mutableListOf()
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
    val price: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isFavorite")
    val isFavorite: Boolean?,
    @SerializedName("description")
    val description: String?
)