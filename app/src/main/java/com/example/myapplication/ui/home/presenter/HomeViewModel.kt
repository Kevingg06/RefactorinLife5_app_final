package com.example.myapplication.ui.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateProduct>()
    val data: LiveData<StateProduct> = _data

    fun getHomeInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateProduct.Loading)

            val response = repository.getInfoHome()

            response.let { info ->
                info.productTypes.let {
                    if (it.isSuccessful) {
                        _data.postValue(
                            it.body()?.let { it1 -> StateProduct.SuccessProductType(it1) })
                    } else {
                        _data.postValue(StateProduct.Error(Constants.PRODUCT_TYPE_FAILED))
                    }
                }

                info.products.let {
                    if (it.isSuccessful) {
                        _data.postValue(it.body()?.let { it1 -> StateProduct.SuccessProducts(it1) })
                    } else {
                        _data.postValue(StateProduct.Error(Constants.PRODUCTS_FAILED))
                    }
                }

                info.lastUserProduct.let {
                    if (it.isSuccessful) {
                        _data.postValue(
                            it.body()?.let { it1 -> StateProduct.SuccessLastUserProduct(it1) })
                    } else {
                        _data.postValue(StateProduct.Error(Constants.LAST_USER_PRODUCT_FAILED))
                    }
                }

                info.dailyOffer.let {
                    if (it.isSuccessful) {
                        _data.postValue(
                            it.body()?.let { it1 -> StateProduct.SuccessDailyOffer(it1) })
                    } else {
                        _data.postValue(StateProduct.Error(Constants.DAILY_OFFER_FAILED))
                    }
                }
            }
        }
    }

}
