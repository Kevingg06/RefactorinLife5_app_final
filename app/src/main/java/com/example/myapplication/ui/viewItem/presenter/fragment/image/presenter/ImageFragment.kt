package com.example.myapplication.ui.viewItem.presenter.fragment.image.presenter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.dto.response.ProductByIdResponse
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_ID
import com.example.myapplication.databinding.FragmentImageBinding
import com.example.myapplication.ui.adapter.ProductImagesAdapter
import com.example.myapplication.ui.home.presenter.HomeActivity

class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ImagesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {bundle ->
            val idProduct = bundle.getInt(ARG_PRODUCT_ID)
            getProduct(idProduct)
        }

        observeState()
        actions()
    }

    private fun initRecyclerView(product: ProductByIdResponse){
        binding.recyclerView.adapter = ProductImagesAdapter(product)
    }

    private fun getProduct(id: Int){
        viewModel.getSimilarProducts(id)
    }

    private fun hideLoading(){
        binding.loadingScreenImages.rlLoading.visibility = View.GONE
    }

    private fun showLoading(){
        binding.loadingScreenImages.rlLoading.visibility = View.VISIBLE
    }

    private fun observeState(){
        viewModel.data.observe(viewLifecycleOwner){ data ->
            when(data){
                is StateProductById.Success -> {
                    initRecyclerView(data.info)
                    render(data.info)
                }

                is StateProductById.Error -> hideLoading()

                is StateProductById.Loading -> showLoading()
            }
        }
    }

    private fun render(value: ProductByIdResponse){
        binding.imagesTvTitle.text = value.name
        binding.ivProductPrice.text = value.price.toString()
        binding.ivProductCurrency.text = value.currency
    }

    private fun actions(){
        binding.arrowButton.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }

        binding.productButton.setOnClickListener {
            binding.productButton.setBackgroundResource(R.drawable.bg_btn_pressed)
            binding.colorsButton.setBackgroundResource(R.drawable.bg_btn_normal)
            binding.similarButton.setBackgroundResource(R.drawable.bg_btn_normal)
        }

        binding.colorsButton.setOnClickListener {
            binding.colorsButton.setBackgroundResource(R.drawable.bg_btn_pressed)
            binding.productButton.setBackgroundResource(R.drawable.bg_btn_normal)
            binding.similarButton.setBackgroundResource(R.drawable.bg_btn_normal)
        }

        binding.similarButton.setOnClickListener {
            binding.similarButton.setBackgroundResource(R.drawable.bg_btn_pressed)
            binding.productButton.setBackgroundResource(R.drawable.bg_btn_normal)
            binding.colorsButton.setBackgroundResource(R.drawable.bg_btn_normal)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(productId: Int): ImageFragment {
            val fragment = ImageFragment()
            val bundle = Bundle().apply {
                putInt(ARG_PRODUCT_ID, productId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
