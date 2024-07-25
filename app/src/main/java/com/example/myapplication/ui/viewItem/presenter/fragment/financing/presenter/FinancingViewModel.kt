package com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StatePaymentMethods
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.data.repository.PaymentRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FinancingViewModel(private val repository: PaymentRepository = PaymentRepository()) : ViewModel() {

    private val _paymentMethods = MutableLiveData<StatePaymentMethods>()
    val paymentMethods: LiveData<StatePaymentMethods> get() = _paymentMethods

    fun getPaymentMethods() {
        viewModelScope.launch {
            _paymentMethods.postValue(StatePaymentMethods.Loading)
            try {
                val response = repository.getPaymentMethods()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _paymentMethods.postValue(StatePaymentMethods.Success(it.paymentMethods))
                    } ?: _paymentMethods.postValue(StatePaymentMethods.Error("Error en el formato de respuesta"))
                } else {
                    _paymentMethods.postValue(StatePaymentMethods.Error("Error en la red"))
                }
            } catch (e: Exception) {
                _paymentMethods.postValue(StatePaymentMethods.Error("Error en la solicitud"))
            }
        }
    }
}
