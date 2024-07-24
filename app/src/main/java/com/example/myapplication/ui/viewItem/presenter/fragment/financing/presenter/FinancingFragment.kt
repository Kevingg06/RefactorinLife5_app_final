package com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.data.dto.model.StatePaymentMethods
import com.example.myapplication.databinding.FragmentFinancingBinding
import com.example.myapplication.ui.adapter.PaymentMethodsAdapter

class FinancingFragment : Fragment() {

    private var _binding: FragmentFinancingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FinancingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinancingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = PaymentMethodsAdapter()
    }

    private fun observeState() {
        viewModel.data.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StatePaymentMethods.Success -> {
                    (binding.recyclerView.adapter as PaymentMethodsAdapter).submitList(state.info)
                    hideLoading()
                }
                is StatePaymentMethods.Error -> hideLoading()
                is StatePaymentMethods.Loading -> showLoading()
            }
        }
    }

    private fun showLoading() {
        binding.loadingScreenFinancing.rlLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingScreenFinancing.rlLoading.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FinancingFragment()
    }
}