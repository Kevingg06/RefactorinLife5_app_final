package com.example.myapplication.ui.viewItem.presenter.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.StateProductDescription
import com.example.myapplication.data.dto.response.ProductExact
import com.example.myapplication.databinding.FragmentDescriptionBinding
import com.example.myapplication.ui.viewItem.presenter.activity.DetailsActivity
import com.example.myapplication.ui.viewItem.presenter.fragment.viewmodel.DescriptionFragmentViewModel

class DescriptionFragment : Fragment() {

    private val viewModel by viewModels<DescriptionFragmentViewModel>()

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idMainProduct = arguments?.getInt(ARG_ID_MAIN_PRODUCT)

        actions()
        idMainProduct?.let { callProductoInfo(it) }
        observeProductInfo()
    }

    private fun actions() {
        binding.ivLeftArrow.setOnClickListener {
            activity?.finish()
        }
    }

    private fun showProduct(info: ProductExact) {
        binding.tvProductName.text = info.name
        binding.tvProductDescription.text = info.description
        binding.tvProductPrice.text = info.price.toString()
    }

    private fun showLoading() {
        binding.frDescRlLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.frDescRlLoading.visibility = View.GONE
    }

    private fun callProductoInfo(idMainProduct: Int){
        viewModel.getProduct(idMainProduct)
    }

    private fun observeProductInfo() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            when (data) {
                is StateProductDescription.SuccessProduct -> {
                    hideLoading()
                    showProduct(data.info)
                }

                is StateProductDescription.Loading -> {
                    showLoading()
                }

                is StateProductDescription.Error -> {

                }
            }
        }
    }

    companion object {
        private const val ARG_ID_MAIN_PRODUCT = "ID_MAIN_PRODUCT"

        fun newInstance(idMainProduct: Int): DescriptionFragment {
            val fragment = DescriptionFragment()
            val args = Bundle()
            args.putInt(ARG_ID_MAIN_PRODUCT, idMainProduct)
            fragment.arguments = args
            return fragment
        }
    }
}