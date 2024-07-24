package com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StatePaymentMethods
import com.example.myapplication.data.repository.PaymentRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FinancingViewModel(private val repository: PaymentRepository = PaymentRepository()) : ViewModel() {

    private val _data = MutableLiveData<StatePaymentMethods>()
    val data: LiveData<StatePaymentMethods> = _data

    init {
        getPaymentMethods()
    }

    private fun getPaymentMethods() {
        viewModelScope.launch {
            _data.value = StatePaymentMethods.Loading
            try {
                val response = repository.getPaymentMethods()
                if (response.isSuccessful) {
                    _data.value = StatePaymentMethods.Success(response.body()?.paymentMethods ?: emptyList())
                } else {
                    _data.value = StatePaymentMethods.Error("Error fetching payment methods")
                }
            } catch (e: Exception) {
                _data.value = StatePaymentMethods.Error(e.message ?: "Unknown error")
            }
        }
    }
}
