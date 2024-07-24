package com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.data.dto.model.StatePaymentMethods
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.databinding.FragmentFinancingBinding
import com.example.myapplication.ui.adapter.PaymentMethodsAdapter

class FinancingFragment : Fragment() {

    private var _binding: FragmentFinancingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FinancingViewModel>()

    companion object {
        @JvmStatic
        fun newInstance(): FinancingFragment {
            return FinancingFragment().apply {
                // You can pass arguments here if needed using Bundle
                arguments = Bundle().apply {
                    // put arguments here
                }
            }
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
        viewModel.paymentMethods.observe(viewLifecycleOwner, Observer { state ->
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
                    // Handle error
                }
            }
        })
    }

    private fun setupRecyclerView(paymentMethods: List<PaymentMethod>) {
        binding.recyclerView.adapter = PaymentMethodsAdapter(paymentMethods)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}