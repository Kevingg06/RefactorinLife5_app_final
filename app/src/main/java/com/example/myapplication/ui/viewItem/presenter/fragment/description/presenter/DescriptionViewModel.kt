package com.example.myapplication.ui.viewItem.presenter.fragment.description.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionViewModel (private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateProductById>()
    val data: LiveData<StateProductById> = _data

    fun getProductById(idProduct: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProductById.Loading)
            val response = repository.getProductById(idProduct)
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateProductById.Success(it))
                } ?: {
                    _data.postValue(StateProductById.Error(Constants.LOGIN_FAILED))
                }
            } else {
                _data.postValue(StateProductById.Error(Constants.NETWORK_ERROR))
            }
        }
    }
}