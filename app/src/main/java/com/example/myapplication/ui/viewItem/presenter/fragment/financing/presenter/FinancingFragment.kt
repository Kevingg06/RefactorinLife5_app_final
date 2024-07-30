package com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.dto.model.StatePaymentMethods
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.data.dto.response.ProductByIdResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.databinding.FragmentFinancingBinding
import com.example.myapplication.ui.adapter.PaymentMethodAdapter
import com.example.myapplication.ui.utils.transformPrice

class FinancingFragment : Fragment() {

    private var _binding: FragmentFinancingBinding? = null
    private val binding get() = _binding!!
    private lateinit var financingViewModel: FinancingViewModel

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
        financingViewModel = ViewModelProvider(this)[FinancingViewModel::class.java]

        arguments?.let { bundle ->
            val idProduct = bundle.getInt(Constants.ARG_PRODUCT_ID)
            getProduct(idProduct)
        }

        actions()

        financingViewModel.paymentMethods.observe(viewLifecycleOwner){ state ->
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
                    binding.recyclerView.visibility = View.VISIBLE
                    setupRecyclerView(emptyList())
                }
            }
        }

        financingViewModel.dataProduct.observe(viewLifecycleOwner){ state ->
            when (state) {
                is StateProductById.Loading -> {
                    binding.loadingScreenFinancing.rlLoading.visibility = View.VISIBLE
                }

                is StateProductById.Success -> {
                    binding.loadingScreenFinancing.rlLoading.visibility = View.GONE
                    render(state.info)
                }

                is StateProductById.Error -> {
                    binding.loadingScreenFinancing.rlLoading.visibility = View.GONE
                }
            }
        }

        financingViewModel.getPaymentMethods()
    }

    private fun render(value: ProductByIdResponse) {
        binding.ivProductPrice.text = value.price.toString().transformPrice(value.currency?: "")
    }

    private fun getProduct(id: Int) {
        financingViewModel.getProductById(id)
    }

    private fun setupRecyclerView(paymentMethods: List<PaymentMethod>) {
        val adapter = PaymentMethodAdapter(paymentMethods)
        binding.recyclerView.adapter = adapter
    }

    private fun actions() {
        binding.arrowButton.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(productId: Int): FinancingFragment {
            val fragment = FinancingFragment()
            val bundle = Bundle().apply {
                putInt(Constants.ARG_PRODUCT_ID, productId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

}