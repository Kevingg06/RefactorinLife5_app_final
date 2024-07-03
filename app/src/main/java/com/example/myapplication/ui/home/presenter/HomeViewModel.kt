package com.example.myapplication.ui.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.model.StateProductType
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateProductType>()
    val data: LiveData<StateProductType> = _data

    private val _productState = MutableLiveData<StateProduct>()
    val productState: MutableLiveData<StateProduct> = _productState

    fun getProductTypes() {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProductType.Loading)
            val response = repository.getProductTypes()
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateProductType.Success(it))
                } ?: {
                    _data.postValue(StateProductType.Error(Constants.PRODUCT_TYPE_FAILED))
                }
            } else {
                _data.postValue(StateProductType.Error(Constants.NETWORK_ERROR))
            }
        }
    }

    fun getProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            _productState.postValue(StateProduct.Loading)
            val response = repository.getProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                } ?: {
                    _productState.postValue(StateProduct.Error(Constants.PRODUCT_TYPE_FAILED))
                }
            } else {
                _productState.postValue(StateProduct.Error(Constants.NETWORK_ERROR))
            }
        }
    }

    fun getLastUserProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getLastUserProduct()
            if (response.isSuccessful) {
                response.body()?.let {

                }
            } else {

            }
        }
    }

    fun getDailyOffer() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getDailyOffer()
            if (response.isSuccessful) {
                response.body()?.let {

                }
            } else {

            }
        }
    }
}
