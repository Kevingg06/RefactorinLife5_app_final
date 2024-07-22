package com.example.myapplication.ui.viewItem.presenter.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.PaymentMethod
import com.example.myapplication.data.dto.response.PaymentMethodsResponse
import com.example.myapplication.data.repository.PaymentRepository
import com.example.myapplication.data.service.RetrofitInstance
import kotlinx.coroutines.launch

class FinancingViewModel : ViewModel() {

    private val _paymentMethods = MutableLiveData<Resource<PaymentMethodsResponse>>()
    val paymentMethods: LiveData<Resource<PaymentMethodsResponse>> get() = _paymentMethods

    init {
        fetchPaymentMethods()
    }

    private fun fetchPaymentMethods() {
        // Lógica para hacer la llamada a la API y actualizar _paymentMethods
        // Por ejemplo, usando Retrofit:
        // ApiClient.apiService.getPaymentMethods().enqueue(object : Callback<PaymentMethodsResponse> {
        //     override fun onResponse(call: Call<PaymentMethodsResponse>, response: Response<PaymentMethodsResponse>) {
        //         if (response.isSuccessful) {
        //             _paymentMethods.value = Resource.Success(response.body())
        //         } else {
        //             _paymentMethods.value = Resource.Error("Error en la respuesta")
        //         }
        //     }
        //     override fun onFailure(call: Call<PaymentMethodsResponse>, t: Throwable) {
        //         _paymentMethods.value = Resource.Error(t.message)
        //     }
        // })
    }
}