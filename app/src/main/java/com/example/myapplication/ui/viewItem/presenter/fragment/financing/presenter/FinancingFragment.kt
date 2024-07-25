package com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.dto.model.StatePaymentMethods
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.databinding.FragmentFinancingBinding
import com.example.myapplication.ui.adapter.PaymentMethodAdapter

    class FinancingFragment : Fragment() {

        private var _binding: FragmentFinancingBinding? = null
        private val binding get() = _binding!!
        private lateinit var financingViewModel: FinancingViewModel

        companion object {
            fun newInstance(): FinancingFragment {
                return FinancingFragment()
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentFinancingBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            financingViewModel = ViewModelProvider(this).get(FinancingViewModel::class.java)

            financingViewModel.paymentMethods.observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    is StatePaymentMethods.Loading -> {
                        binding.loadingScreenFinancing.rlLoading.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    is StatePaymentMethods.Success -> {
                        binding.loadingScreenFinancing.rlLoading.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        setupRecyclerView(state.info)
                    }
                    is StatePaymentMethods.Error -> {
                        binding.loadingScreenFinancing.rlLoading.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            })

            financingViewModel.getPaymentMethods()
        }

        private fun setupRecyclerView(paymentMethods: List<PaymentMethod>) {
            val adapter = PaymentMethodAdapter(paymentMethods)
            binding.recyclerView.adapter = adapter
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }