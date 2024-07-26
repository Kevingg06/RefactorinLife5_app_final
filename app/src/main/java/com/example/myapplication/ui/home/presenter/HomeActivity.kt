package com.example.myapplication.ui.home.presenter

import android.content.Intent
import com.example.myapplication.ui.adapter.ProductTypesAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.dto.dataSource.getToken
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.data.dto.response.ProductType
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_ID
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_TYPE_ID
import com.example.myapplication.data.utils.TokenHolder.savedToken
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.ui.adapter.AdapterProduct
import com.example.myapplication.ui.search.presenter.SearchActivity
import com.example.myapplication.ui.viewItem.presenter.activity.DetailsActivity
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity(), ProductTypesAdapter.OnCategoryClickListener {
    private val viewModel by viewModels<HomeViewModel>()
    private var idMainProduct: Int? = null
    private var idProductType: Int = 1
    private lateinit var binding: ActivityHomeBinding
    private var productsAdapter: AdapterProduct? = null

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       token = getToken(this)

        token?.let {
            savedToken = it
        }

        actions()
        initFavoriteIcon()
        setupRecyclerViews()
        getHomeInfo()
        observerHomeInfo()
        observeFavorites()
    }

    private fun actions() {
        binding.retryMessage.setOnClickListener {
            hideError()
            token?.let {
                getHomeInfo()
            }
        }

        binding.mainSaleLayout.setOnClickListener {
            val myIntent = Intent(this, DetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(ARG_PRODUCT_ID, idMainProduct?: -1)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
        }

        binding.clickableOverlay.setOnClickListener {
            val myIntent = Intent(this, SearchActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(ARG_PRODUCT_TYPE_ID, idProductType?: 1)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
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

    private fun setFavorite( id: Int) {
        viewModel.putFavorites(id)
    }

    private fun setRecyclerView(
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
                    setRecyclerView(data.info.productTypes, this)
                }

                is StateProduct.SuccessProducts -> {
                    hideLoading()
                    setRecyclerViewProduct(data.info.products)
                }

                is StateProduct.SuccessLastUserProduct -> hideLoading()


                is StateProduct.SuccessDailyOffer -> {
                    hideLoading()
                    setProductDailyOffer(data.info)
                    idMainProduct = data.info.idProduct
                }

                is StateProduct.SuccessFavorites -> hideLoading()


                is StateProduct.Loading -> showLoading()


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

    private fun observeFavorites() {
        viewModel.isFavorite.observe(this) { isFavorite ->
            setFavoriteIcon(isFavorite)
        }
    }

    private fun setFavoriteData(iconState: Boolean) {
        viewModel.setFavoriteData(iconState)
    }

    private fun initFavoriteIcon() {
        binding.ivAddFavorites.setOnClickListener {
            val buttonState = viewModel.isFavorite.value ?: false
            val currentButtonState = !buttonState
            setFavoriteData(currentButtonState)
                idMainProduct?.let {
                    setFavorite(it)
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
            binding.productName.text = singleProductResponse.name
            binding.productDescription.text = singleProductResponse.description
            binding.productPrice.text =
                "${singleProductResponse.currency} ${singleProductResponse.price}"

            if(!singleProductResponse.images.isNullOrEmpty())
                Picasso.get().load(singleProductResponse.images[0].link).into(binding.imageMainProduct)

            singleProductResponse.isFavorite?.let {
                setFavoriteData(it)
            }
        }
    }

    override fun onCategoryClick(category: Int) {
        idProductType = category
        viewModel.filterProductsByCategory(category)
    }

    private fun updateFilteredProducts(products: MutableList<Product>?) {
        runOnUiThread {
            productsAdapter?.updateProducts(products)
            productsAdapter?.notifyDataSetChanged()
        }
    }
}
