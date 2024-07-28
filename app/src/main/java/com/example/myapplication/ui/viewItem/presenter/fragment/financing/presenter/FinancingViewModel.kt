package com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StatePaymentMethods
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.repository.PaymentRepository
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.launch

class FinancingViewModel(
    private val repository: PaymentRepository = PaymentRepository(),
    private val repositoryProduct: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _paymentMethods = MutableLiveData<StatePaymentMethods>()
    val paymentMethods: LiveData<StatePaymentMethods> get() = _paymentMethods

    fun getPaymentMethods() {
        viewModelScope.launch {
            _paymentMethods.postValue(StatePaymentMethods.Loading)
            val response = repository.getPaymentMethods()
            if (response.isSuccessful) {
                response.body()?.let {
                    _paymentMethods.postValue(StatePaymentMethods.Success(it.paymentMethods))
                }
                    ?: _paymentMethods.postValue(StatePaymentMethods.Error(Constants.PRODUCT_PAYMENT_METHOD_FAILED))
            } else {
                _paymentMethods.postValue(StatePaymentMethods.Error(Constants.NETWORK_ERROR))
            }
        }
    }

    private val _dataProduct = MutableLiveData<StateProductById>()
    val dataProduct: LiveData<StateProductById> = _dataProduct

    fun getProductById(id: Int) {
        viewModelScope.launch {
            _dataProduct.postValue(StateProductById.Loading)
            val response = repositoryProduct.getProductById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _dataProduct.postValue(StateProductById.Success(it))
                } ?: _dataProduct.postValue(StateProductById.Error(Constants.PRODUCTS_FAILED))
            } else {
                _dataProduct.postValue(StateProductById.Error(Constants.NETWORK_ERROR))
            }
        }
    }
}
