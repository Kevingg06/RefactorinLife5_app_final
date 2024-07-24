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

class FinancingViewModel : ViewModel() {

    private val repository = PaymentRepository()

    private val _paymentMethods = MutableLiveData<StatePaymentMethods>()
    val paymentMethods: LiveData<StatePaymentMethods> get() = _paymentMethods

    fun getPaymentMethods() {
        _paymentMethods.value = StatePaymentMethods.Loading
        viewModelScope.launch {
            try {
                val response = repository.getPaymentMethods()
                _paymentMethods.value = if (response.isSuccessful) {
                    StatePaymentMethods.Success(response.body()?.paymentMethods ?: emptyList())
                } else {
                    StatePaymentMethods.Error("Error fetching data")
                }
            } catch (e: Exception) {
                _paymentMethods.value = StatePaymentMethods.Error("Error: ${e.message}")
            }
        }
    }
}
