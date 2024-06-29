package com.example.myapplication.data.dto.response

import com.google.gson.annotations.SerializedName

data class ProductTypesResponse (
    @SerializedName("productTypes")
    val productTypes: MutableList<ProductType>?
)

data class ProductType (
    @SerializedName("idProductType")
    val idProductType: Int?,
    @SerializedName("description")
    val description: String?
)