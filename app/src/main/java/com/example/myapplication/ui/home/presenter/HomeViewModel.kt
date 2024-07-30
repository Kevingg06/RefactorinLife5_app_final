package com.example.myapplication.ui.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.response.Product
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

    private var allProducts: MutableList<Product>? = mutableListOf()

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getHomeInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProduct.Loading)
            val response = repository.getInfoHome()

            if (!processProductTypes(response.productTypes)) return@launch
            if (!processProducts(response.products)) return@launch
            if (!processDailyOffer(response.dailyOffer)) return@launch
        }
    }

    fun putFavorites(id: Int) {
        viewModelScope.launch {
            val response = repository.updateFavoriteProduct(id)

            if (response.isSuccessful) {
                _data.postValue(StateProduct.SuccessFavorites)
            } else {
                _data.postValue(StateProduct.Error(Constants.PRODUCT_NOT_UPDATED))
            }
        }
    }

    fun setFavoriteData(isFavoriteProduct: Boolean?) {
        _isFavorite.postValue(isFavoriteProduct ?: false)
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
                    allProducts = body.products
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

    fun filterProductsByCategory(category: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val filteredProducts =
                allProducts?.filter { it.productType?.idProductType == category }
                    ?.toMutableList()
            withContext(Dispatchers.Main) {
                _data.postValue(filteredProducts?.let { StateProduct.FilteredProducts(it) })
            }
        }
    }
}

