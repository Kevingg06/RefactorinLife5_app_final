package com.example.myapplication.ui.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateProductType
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateProductType>()
    val data: LiveData<StateProductType> = _data

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
}