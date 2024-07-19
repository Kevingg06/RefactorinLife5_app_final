package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.model.StateSimilar
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.launch


class ImagesViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {

    private val _data = MutableLiveData<StateSimilar>()
    val data: LiveData<StateSimilar> = _data

    fun getSimilarProducts(id: Int) {
        viewModelScope.launch {
            val response = repository.getSimilarProducts(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateSimilar.Success(it))
                } ?: _data.postValue(StateSimilar.Error(Constants.PRODUCTS_FAILED))
            } else {
                _data.postValue(StateSimilar.Error(Constants.NETWORK_ERROR))
            }
        }
    }

}
