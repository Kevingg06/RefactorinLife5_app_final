package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.service.ProductServiceImp
import retrofit2.Response

class ProductRepository(private val service: ProductServiceImp = ProductServiceImp()) {
    suspend fun getProductTypes(): Response<ProductTypesResponse> {
        return service.getProductTypes()
    }

    suspend fun getProducts(): Response<ProductsResponse> {
        return service.getProducts()
    }

    suspend fun getLastUserProduct(): Response<SingleProductResponse> {
        return service.getLastUserProduct()
    }

    suspend fun getDailyOffer(): Response<SingleProductResponse> {
        return service.getDailyOffer()
    }

    suspend fun getInfoHome(): ObjectComplete {
        val resultProductTypes = service.getProductTypes()
        val resultProducts = service.getProducts()
        val resultLastUserProduct = service.getLastUserProduct()
        val resultDailyOffer = service.getDailyOffer()

        val objectComplete = ObjectComplete(
            productTypes = resultProductTypes,
            products = resultProducts,
            lastUserProduct = resultLastUserProduct,
            dailyOffer = resultDailyOffer
        )

        return objectComplete
    }
}

data class ObjectComplete(
    val productTypes: Response<ProductTypesResponse>,
    val products: Response<ProductsResponse>,
    val lastUserProduct: Response<SingleProductResponse>,
    val dailyOffer: Response<SingleProductResponse>
) {

}
