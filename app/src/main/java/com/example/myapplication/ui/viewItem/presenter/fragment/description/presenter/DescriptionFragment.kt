package com.example.myapplication.ui.viewItem.presenter.fragment.description.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.dto.response.ProductByIdResponse
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_ID
import com.example.myapplication.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DescriptionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val idProduct = bundle.getInt(ARG_PRODUCT_ID)
            callProductoInfo(idProduct)
        }
        observeProductInfo()
    }

    private fun showProduct(info: ProductByIdResponse) {
        binding.tvProductName.text = info.name
        binding.tvProductDescription.text = info.largeDescription
        binding.ivProductPrice.text = info.price.toString()
        binding.ivProductCurrency.text = info.currency.toString()
    }

    private fun showProductError() {
        binding.tvProductName.text = ""
        binding.tvProductDescription.text = ""
        binding.ivProductPrice.text = ""
        binding.ivProductCurrency.text = ""
    }

    private fun showLoading() {
        binding.loadingScreenImages.rlLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingScreenImages.rlLoading.visibility = View.GONE
    }

    private fun callProductoInfo(idMainProduct: Int) {
        viewModel.getProductById(idMainProduct)
    }

    private fun observeProductInfo() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            when (data) {
                is StateProductById.Success -> {
                    hideLoading()
                    showProduct(data.info)
                }

                is StateProductById.Loading -> {
                    showLoading()
                }

                is StateProductById.Error -> {
                    hideLoading()
                    showProductError()
                }
            }
        }
    }

    companion object {

        fun newInstance(productId: Int): DescriptionFragment {
            val fragment = DescriptionFragment()
            val bundle = Bundle().apply {
                putInt(ARG_PRODUCT_ID, productId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}