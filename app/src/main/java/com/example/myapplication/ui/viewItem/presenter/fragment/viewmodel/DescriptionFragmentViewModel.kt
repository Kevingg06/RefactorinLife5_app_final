package com.example.myapplication.ui.viewItem.presenter.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateProductDescription
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionFragmentViewModel (private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateProductDescription>()
    val data: LiveData<StateProductDescription> = _data

    fun getProduct(idProduct: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProductDescription.Loading)
            val response = repository.getProduct(idProduct)
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateProductDescription.SuccessProduct(it))
                } ?: {
                    _data.postValue(StateProductDescription.Error(Constants.LOGIN_FAILED))
                }
            } else {
                _data.postValue(StateProductDescription.Error(Constants.NETWORK_ERROR))
            }
        }
    }
}