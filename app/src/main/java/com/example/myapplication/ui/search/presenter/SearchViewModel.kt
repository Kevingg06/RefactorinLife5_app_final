package com.example.myapplication.ui.search.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.model.StateProductSimilar
import com.example.myapplication.data.dto.model.StateProductsSearch
import com.example.myapplication.data.dto.model.StateRegister
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {

    private val _data = MutableLiveData<StateProductsSearch>()
    val data: LiveData<StateProductsSearch> = _data

    private val _data2 = MutableLiveData<StateProduct>()
    val data2: LiveData<StateProduct> = _data2

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun searchProducts(idProductType: Int, productName: String, onlyFavorite: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProductsSearch.Loading)
            val response = repository.searchProducts(idProductType, productName, onlyFavorite)
            if (response.isSuccessful) {
                response.body()?.let {
                    val sizeProducts = it.products?.size ?: 0

                    if (sizeProducts > 0){
                        _data.postValue(StateProductsSearch.Success(it))
                    }else{
                        _data.postValue(StateProductsSearch.NoContent)
                    }
                } ?: {
                    _data.postValue(StateProductsSearch.Error(Constants.LOGIN_FAILED))
                }
            } else {
                _data.postValue(StateProductsSearch.Error(Constants.NETWORK_ERROR))
            }
        }
    }

    fun putFavorites(id: Int) {
        viewModelScope.launch {
            val response = repository.updateFavoriteProduct(id)

            if (response.isSuccessful) {
                _data2.postValue(StateProduct.SuccessFavorites)
            } else {
                _data2.postValue(StateProduct.Error(Constants.PRODUCT_NOT_UPDATED))
            }
        }
    }

    fun setFavoriteData(isFavoriteProduct: Boolean?) {
        _isFavorite.postValue(isFavoriteProduct ?: false)
    }

}