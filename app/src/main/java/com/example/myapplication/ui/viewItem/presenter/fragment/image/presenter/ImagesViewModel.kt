package com.example.myapplication.ui.viewItem.presenter.fragment.image.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.launch


class ImagesViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {

    private val _data = MutableLiveData<StateProductById>()
    val data: LiveData<StateProductById> = _data

    fun getSimilarProducts(id: Int) {
        viewModelScope.launch {
            val response = repository.getProductById(id)
            _data.postValue(StateProductById.Loading)
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateProductById.Success(it))
                } ?: _data.postValue(StateProductById.Error(Constants.PRODUCTS_FAILED))
            } else {
                _data.postValue(StateProductById.Error(Constants.NETWORK_ERROR))
            }
        }
    }

}
