package com.example.myapplication.ui.home.presenter

import ProductTypesAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.StateProductType
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

        getProducts()
        getLastUserProduct()
        getDailyOffer()
    }

    private fun getProductTypes(){
        viewModel.getProductTypes()
    }

    private fun getProducts() {
        viewModel.getProducts()
    }

    private fun getLastUserProduct() {
        viewModel.getLastUserProduct()
    }

    private fun getDailyOffer() {
        viewModel.getDailyOffer()
    }

    private fun setRecicleView(value: ProductTypesResponse) {
        val adapter = ProductTypesAdapter(value)
        binding.rvCategoriesHome.adapter = adapter
    }
    private fun observerProductTypes() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateProductType.Success -> {
                    setRecicleView(data.info)
                }
                is StateProductType.Loading -> {
                }
                is StateProductType.Error -> {
                }
            }
        }
    }
}
