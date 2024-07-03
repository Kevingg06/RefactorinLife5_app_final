package com.example.myapplication.ui.home.presenter

import com.example.myapplication.ui.adapter.ProductTypesAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.data.dto.response.ProductsResponse
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.ui.adapter.AdapterProduct
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerViews()
        getHomeInfo()
        observerHomeInfo()
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

    private fun setRecicleView(value: ProductTypesResponse) {
        runOnUiThread {
            val adapter = ProductTypesAdapter(value)
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

    private fun observerHomeInfo() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateProduct.SuccessProductType -> {
                    hideLoading()
                    setRecicleView(data.info)
                    Log.v("MIO", "1111");
                    Toast.makeText(this,"1111", Toast.LENGTH_SHORT).show()
                }
                is StateProduct.SuccessProducts -> {
                    hideLoading()
                    setRecyclerViewProduct(data.info)
                    Log.v("MIO", "2222");
                    Toast.makeText(this,"2222", Toast.LENGTH_SHORT).show()
                }
                is StateProduct.SuccessLastUserProduct -> {
                    hideLoading()
                    Log.v("MIO", "3333");
                    Toast.makeText(this,"3333", Toast.LENGTH_SHORT).show()
                }
                is StateProduct.SuccessDailyOffer -> {
                    hideLoading()
                    setProductDailyOffer(data.info)
                    Log.v("MIO", "4444");
                    Toast.makeText(this,"4444", Toast.LENGTH_SHORT).show()
                }
                is StateProduct.Loading -> {
                    showLoading()
                }
                is StateProduct.Error -> {
                    hideLoading()
                    showError()
                }
            }
        }
    }

    private fun setRecyclerViewProduct(value: ProductsResponse) {
        runOnUiThread {
            val adapter = AdapterProduct(value)
            binding.rvRecommendationsHome.adapter = adapter
        }
    }

    private fun setProductDailyOffer(singleProductResponse: SingleProductResponse) {
        runOnUiThread {
            binding.tvStateProduct.text = Constants.DAILY_OFFER_STATE
            Picasso.get().load(singleProductResponse.image).into(binding.imageMainProduct)
            binding.productName.text = singleProductResponse.name
            binding.productDescription.text = singleProductResponse.description
            binding.productPrice.text = "${singleProductResponse.currency} ${singleProductResponse.price}"
        }
    }
}
