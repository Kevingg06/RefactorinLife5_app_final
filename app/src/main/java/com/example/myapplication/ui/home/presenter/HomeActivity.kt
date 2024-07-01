package com.example.myapplication.ui.home.presenter

import ProductTypesAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getProductTypes()
        observerProductTypes()
    }

    private fun getProductTypes(){
        viewModel.getProductTypes()
    }

    private fun setRecicleView(value: ProductTypesResponse) {
        val adapter = ProductTypesAdapter(value)
        binding.rvCategoriesHome.adapter = adapter
    }

    private fun showLoading(){
        binding.loadingScreen.rlLoading.visibility = View.VISIBLE
    }
    private fun hideLoading(){
        binding.loadingScreen.rlLoading.visibility = View.GONE
    }

    private fun showError(){
        binding.rvRecommendationsHome.visibility = View.GONE
        binding.rvCategoriesHome.visibility = View.GONE
        binding.mainSaleLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
    }

    private fun observerProductTypes() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateProduct.SuccessProductType -> {
                    setRecicleView(data.info)
                }
                is StateProduct.SuccessProducts -> {
                }
                is StateProduct.SuccessLastProduct -> {
                }
                is StateProduct.SuccessDailyOffer -> {
                }
                is StateProduct.Loading -> {
                    showLoading()
                }
                is StateProduct.Error -> {
                    showError()
                }
            }
        }
    }
}
