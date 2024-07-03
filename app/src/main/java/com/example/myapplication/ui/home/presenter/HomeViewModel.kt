package com.example.myapplication.ui.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class HomeViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateProduct>()
    val data: LiveData<StateProduct> = _data

    fun getHomeInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProduct.Loading)
            val response = repository.getInfoHome()

            response.let { info ->
                processProductTypes(info.productTypes)
                processProducts(info.products)
                processLastUserProduct(info.lastUserProduct)
                processDailyOffer(info.dailyOffer)
            }
        }
    }

    private suspend fun processProductTypes(productTypes: Response<ProductTypesResponse>?) {
        productTypes?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessProductType(body))
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.PRODUCT_TYPE_FAILED))
                }
            }
        }
    }

    private suspend fun processProducts(products: Response<ProductsResponse>?) {
        products?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessProducts(body))
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.PRODUCTS_FAILED))
                }
            }
        }
    }

    private suspend fun processLastUserProduct(lastUserProduct: Response<SingleProductResponse>?) {
        lastUserProduct?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessLastUserProduct(body))
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.LAST_USER_PRODUCT_FAILED))
                }
            }
        }
    }

    private suspend fun processDailyOffer(dailyOffer: Response<SingleProductResponse>?) {
        dailyOffer?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessDailyOffer(body))
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.DAILY_OFFER_FAILED))
                }
            }
        }
    }
}
