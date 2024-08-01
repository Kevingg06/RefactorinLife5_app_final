package com.example.myapplication.ui.viewItem.presenter.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StateDailyOffer
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: ProductRepository = ProductRepository()) :
    ViewModel() {

    private val _data = MutableLiveData<StateDailyOffer>()
    val data: LiveData<StateDailyOffer> = _data

    fun setNewDailyOffer(id: Int) {
        viewModelScope.launch {
            _data.postValue(StateDailyOffer.Loading)
            val response = repository.getProductById(id)
            if (response.isSuccessful) {
                _data.postValue(StateDailyOffer.Success)
            } else {
                _data.postValue(StateDailyOffer.Error(Constants.PRODUCT_NOT_UPDATED))
            }
        }
    }
}