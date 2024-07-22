package com.example.myapplication.ui.viewItem.presenter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.PaymentMethodsResponse
import com.example.myapplication.databinding.FragmentFinancingBinding
import com.example.myapplication.ui.adapter.InstallmentsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class FinancingFragment : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_financing, container, false)
//    }
//
//    companion object {
//        fun newInstance(): FinancingFragment {
//            val fragment = FinancingFragment()
//            return fragment
//        }
//    }
//}

class FinancingFragment : Fragment() {

    private lateinit var binding: FragmentFinancingBinding
//    private lateinit var paymentMethods: List<PaymentMethod> // Asume que tienes una clase PaymentMethod

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinancingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPaymentMethods()
    }

    private fun fetchPaymentMethods() {
//        // Llama a tu API para obtener los métodos de pago
////        val apiService = ApiService.create()
//        apiService.getPaymentMethods().enqueue(object : Callback<PaymentMethodsResponse> {
//            override fun onResponse(
//                call: Call<PaymentMethodsResponse>,
//                response: Response<PaymentMethodsResponse>
//            ) {
//                if (response.isSuccessful) {
//                    paymentMethods = response.body()?.paymentMethods ?: emptyList()
//                    setupRecyclerView()
//                }
//            }
//
//            override fun onFailure(call: Call<PaymentMethodsResponse>, t: Throwable) {
//                // Manejar errores aquí
//            }
//        })
    }
}
    private fun setupRecyclerView() {
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = InstallmentsAdapter(paymentMethods)
//        }
//    }
}
