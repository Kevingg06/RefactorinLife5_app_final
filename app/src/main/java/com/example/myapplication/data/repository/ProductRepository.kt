package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.request.LoginRequest
import com.example.myapplication.data.dto.response.LoginResponse
import com.example.myapplication.data.dto.response.ProductExact
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.service.ProductServiceImp
import retrofit2.Response

class ProductRepository(private val service: ProductServiceImp = ProductServiceImp()) {

    suspend fun getInfoHome(): ObjectComplete {
        val resultProductTypes = service.getProductTypes()
        val resultProducts = service.getProducts()
        val resultDailyOffer = service.getDailyOffer()
        val objectComplete = ObjectComplete(
            productTypes = resultProductTypes,
            products = resultProducts,
            dailyOffer = resultDailyOffer
        )
        return objectComplete
    }

    suspend fun getProduct(idProduct : Int): Response<ProductExact> {
        return service.getProduct(idProduct)
    }

    suspend fun updateFavoriteProduct(productId: Int): Response<Unit>{
        return service.updateFavoriteProduct(productId)
    }
}

data class ObjectComplete(
    val productTypes: Response<ProductTypesResponse>,
    val products: Response<ProductsResponse>,
    val dailyOffer: Response<SingleProductResponse>
)
