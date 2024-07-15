package com.example.myapplication.ui.home.presenter

import com.example.myapplication.ui.adapter.ProductTypesAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.data.dto.response.ProductType
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.service.ProductServiceImp
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.ui.adapter.AdapterProduct
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity(), ProductTypesAdapter.OnCategoryClickListener {
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding
    private var productsAdapter: AdapterProduct? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        actions()
        setupRecyclerViews()
        getHomeInfo()
        observerHomeInfo()
    }

    private fun actions() {
        binding.retryMessage.setOnClickListener {
            hideError()
            getHomeInfo()
        }
    }

    private fun setupRecyclerViews() {
        binding.rvCategoriesHome.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommendationsHome.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getHomeInfo() {
        viewModel.getHomeInfo()
    }

    private fun setRecicleView(
        value: MutableList<ProductType>?,
        listener: ProductTypesAdapter.OnCategoryClickListener
    ) {
        runOnUiThread {
            val adapter = ProductTypesAdapter(value, listener)
            binding.rvCategoriesHome.adapter = adapter
        }
    }

    private fun showLoading() {
        runOnUiThread {
            binding.loadingScreen.rlLoading.visibility = View.VISIBLE
            binding.rvRecommendationsHome.visibility = View.GONE
            binding.rvCategoriesHome.visibility = View.GONE
            binding.mainSaleLayout.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        runOnUiThread {
            binding.loadingScreen.rlLoading.visibility = View.GONE
            binding.rvRecommendationsHome.visibility = View.VISIBLE
            binding.rvCategoriesHome.visibility = View.VISIBLE
            binding.mainSaleLayout.visibility = View.VISIBLE
        }
    }

    private fun showError() {
        runOnUiThread {
            binding.rvRecommendationsHome.visibility = View.GONE
            binding.rvCategoriesHome.visibility = View.GONE
            binding.mainSaleLayout.visibility = View.GONE
            binding.errorLayout.visibility = View.VISIBLE
        }
    }

    private fun hideError() {
        runOnUiThread {
            binding.errorLayout.visibility = View.GONE
        }
    }

    private fun observerHomeInfo() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateProduct.SuccessProductType -> {
                    hideLoading()
                    setRecicleView(data.info.productTypes, this)
                }

                is StateProduct.SuccessProducts -> {
                    hideLoading()
                    setRecyclerViewProduct(data.info.products)
                }

                is StateProduct.SuccessLastUserProduct -> {
                    hideLoading()
                }

                is StateProduct.SuccessDailyOffer -> {
                    hideLoading()
                    setProductDailyOffer(data.info)
                }

                is StateProduct.Loading -> {
                    showLoading()
                }

                is StateProduct.Error -> {
                    hideLoading()
                    showError()
                }
                is StateProduct.FilteredProducts -> {
                    hideLoading()
                    updateFilteredProducts(data.products)
                }
            }
        }
    }

    private fun setRecyclerViewProduct(value: MutableList<Product>?) {
        runOnUiThread {
            productsAdapter = AdapterProduct(value)
            binding.rvRecommendationsHome.adapter = productsAdapter
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean?) {
        val favorite = isFavorite ?: false
        if (favorite) {
            binding.ivAddFavorites.setImageResource(R.drawable.icon_favorite_solid)
        } else {
            binding.ivAddFavorites.setImageResource(R.drawable.icon_favorite)
        }
    }

    private fun setProductDailyOffer(singleProductResponse: SingleProductResponse) {
        runOnUiThread {
            binding.tvStateProduct.text = Constants.DAILY_OFFER_STATE
            Picasso.get().load(singleProductResponse.image).into(binding.imageMainProduct)
            binding.productName.text = singleProductResponse.name
            binding.productDescription.text = singleProductResponse.description
            binding.productPrice.text =
                "${singleProductResponse.currency} ${singleProductResponse.price}"
        }
    }

    override fun onCategoryClick(category: Int) {
        viewModel.filterProductsByCategory(category)
        Toast.makeText(this,category.toString(),Toast.LENGTH_SHORT).show()
    }

    private fun updateFilteredProducts(products: MutableList<Product>?) {
        runOnUiThread {
            productsAdapter?.updateProducts(products)
            productsAdapter?.notifyDataSetChanged()
        }
    }
}
