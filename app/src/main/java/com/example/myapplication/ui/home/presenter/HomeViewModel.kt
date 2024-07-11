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

            if (!processProductTypes(response.productTypes)) return@launch
            if (!processProducts(response.products)) return@launch
            if (!processLastUserProduct(response.lastUserProduct)) return@launch
            if (!processDailyOffer(response.dailyOffer)) return@launch
        }
    }

    private suspend fun processProductTypes(productTypes: Response<ProductTypesResponse>?): Boolean {
        productTypes?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessProductType(body))
                    }
                }
                return true
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.PRODUCT_TYPE_FAILED))
                }
                return false
            }
        }
        return false
    }

    private suspend fun processProducts(products: Response<ProductsResponse>?): Boolean {
        products?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessProducts(body))
                    }
                }
                return true
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.PRODUCTS_FAILED))
                }
                return false
            }
        }
        return false
    }

    private suspend fun processLastUserProduct(lastUserProduct: Response<SingleProductResponse>?): Boolean {
        lastUserProduct?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessLastUserProduct(body))
                    }
                }
                return true
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.LAST_USER_PRODUCT_FAILED))
                }
                return false
            }
        }
        return false
    }

    private suspend fun processDailyOffer(dailyOffer: Response<SingleProductResponse>?): Boolean {
        dailyOffer?.let {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    withContext(Dispatchers.Main) {
                        _data.postValue(StateProduct.SuccessDailyOffer(body))
                    }
                }
                return true
            } else {
                withContext(Dispatchers.Main) {
                    _data.postValue(StateProduct.Error(Constants.DAILY_OFFER_FAILED))
                }
                return false
            }
        }
        return false
    }
}
