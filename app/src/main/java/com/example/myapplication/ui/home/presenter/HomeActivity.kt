package com.example.myapplication.ui.home.presenter

import com.example.myapplication.ui.adapter.ProductTypesAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.dto.dataSource.getToken
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
    private var idMainProduct: Int? = null
    private val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcGkgVXNlcnMgU3ViamVjdCIsInJvbGUiOjEsImlzcyI6IkFwaSBVc2VycyIsInVzZXJJZCI6NDYsImlhdCI6MTcyMDkxMzA1NCwianRpIjoiNGQwMjBmZTktMzBiMS00NzZmLWFmNzktZDQ5ZjRiZWM5ZTEzIn0.hY4R-lUD0NyYMeBiuSaVW3XTOENOMa1-rpthfqP_iBU"
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        actions(token)
        initFavoriteIcon(token)
        setupRecyclerViews()
        getHomeInfo(token)
        observerHomeInfo()
        observeFavorites()
    }

    private fun actions(token: String?) {
        binding.retryMessage.setOnClickListener {
            hideError()
            token?.let {
                getHomeInfo("Bearer $token")
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rvCategoriesHome.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommendationsHome.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getHomeInfo(token: String?) {
        token?.let {
            viewModel.getHomeInfo("Bearer $token")
        }
    }

    private fun setFavorite(token: String, id: Int) {
        viewModel.putFavorites(token, id)
    }

    private fun setRecyclerView(value: ProductTypesResponse) {
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
                    setRecyclerView(data.info)
                }

                is StateProduct.SuccessProducts -> {
                    hideLoading()
                    setRecyclerViewProduct(data.info)
                }

                is StateProduct.SuccessLastUserProduct -> {
                    hideLoading()
                }

                is StateProduct.SuccessDailyOffer -> {
                    hideLoading()
                    setProductDailyOffer(data.info)
                    idMainProduct = data.info.idProduct
                }

                is StateProduct.SuccessFavorites -> {
                    hideLoading()
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

    private fun observeFavorites() {
        viewModel.isFavorite.observe(this) { isFavorite ->
            setFavoriteIcon(isFavorite)
        }
    }

    private fun setFavoriteData(iconState: Boolean) {
        viewModel.setFavoriteData(iconState)
    }

    private fun initFavoriteIcon(token: String?) {
        binding.ivAddFavorites.setOnClickListener {
            val buttonState = viewModel.isFavorite.value ?: false
            val currentButtonState = !buttonState
            setFavoriteData(currentButtonState)
            if (token != null) {
                idMainProduct?.let {
                    setFavorite("Bearer $token", it)
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
            binding.productName.text = singleProductResponse.name
            binding.productDescription.text = singleProductResponse.description
            binding.productPrice.text = "${singleProductResponse.currency} ${singleProductResponse.price}"

            if(!singleProductResponse.images.isNullOrEmpty())
                Picasso.get().load(singleProductResponse.images[0].link).into(binding.imageMainProduct)

            singleProductResponse.isFavorite?.let {
                setFavoriteData(it)
            }
        }
    }
}
