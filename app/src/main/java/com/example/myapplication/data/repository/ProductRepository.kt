package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.service.ProductServiceImp
import retrofit2.Response

class ProductRepository(private val service: ProductServiceImp = ProductServiceImp()) {

    suspend fun getInfoHome(token: String): ObjectComplete {
        val resultProductTypes = service.getProductTypes(token)
        val resultProducts = service.getProducts(token)
        val resultDailyOffer = service.getDailyOffer(token)
        val objectComplete = ObjectComplete(
            productTypes = resultProductTypes,
            products = resultProducts,
            dailyOffer = resultDailyOffer
        )
        return objectComplete
    }

    suspend fun updateFavoriteProduct(token: String , productId: Int): Response<Unit>{
        return service.updateFavoriteProduct(token, productId)
    }
}

data class ObjectComplete(
    val productTypes: Response<ProductTypesResponse>,
    val products: Response<ProductsResponse>,
    val dailyOffer: Response<SingleProductResponse>
)
