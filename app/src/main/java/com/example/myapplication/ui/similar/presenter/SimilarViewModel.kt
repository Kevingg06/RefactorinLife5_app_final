package com.example.myapplication.ui.similar.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateProductSimilar
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimilarViewModel (private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateProductSimilar>()
    val data: LiveData<StateProductSimilar> = _data

    fun getSimilarProductsById(idProduct: Int,page: Int, size: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProductSimilar.Loading)
            val response = repository.getSimilarProductsById(idProduct,page,size)
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateProductSimilar.Success(it))
                } ?: {
                    _data.postValue(StateProductSimilar.Error(Constants.LOGIN_FAILED))
                }
            } else {
                _data.postValue(StateProductSimilar.Error(Constants.NETWORK_ERROR))
            }
        }
    }
}