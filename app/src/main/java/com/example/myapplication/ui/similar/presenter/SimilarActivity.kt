package com.example.myapplication.ui.similar.presenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.dto.model.StateProductSimilar
import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_ID
import com.example.myapplication.databinding.ActivitySimilarBinding
import com.example.myapplication.ui.adapter.SimilarProductSearch
import com.example.myapplication.ui.viewItem.presenter.activity.DetailsActivity

class SimilarActivity : AppCompatActivity(), SimilarProductSearch.OnSimilarProductItemClickListener {

    private var idProduct: Int? = null
    private val viewModel by viewModels<SimilarViewModel>()
    private lateinit var binding: ActivitySimilarBinding
    private lateinit var adapter: SimilarProductSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimilarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val bundle = intent.extras
        bundle?.let {
            idProduct = it.getInt(ARG_PRODUCT_ID)
        }

        idProduct?.let { callSimilarProductsById(it) }
        observeProductInfo()
        observeFavoriteStatus()
        actions()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun setupRecyclerView(
    ) {
        adapter = SimilarProductSearch(mutableListOf(), this,this)
        binding.rvSearchSimilar.layoutManager = LinearLayoutManager(this)
        binding.rvSearchSimilar.adapter = adapter
    }

    private fun actions() {
        binding.arrowButton.setOnClickListener {
            finish()
        }
    }

    private fun callSimilarProductsById(idMainProduct: Int) {
        viewModel.getSimilarProductsById(
            idMainProduct,
            Constants.SIZE_DEFAULT,
            Constants.PAGE_DEFAULT
        )
    }

    private fun showLoading() {
        binding.loadingScreenImages.rlLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingScreenImages.rlLoading.visibility = View.GONE
    }

    private fun setRecyclerViewProduct(value: MutableList<Product>?) {
        runOnUiThread {
            adapter.updateData(value)
        }
    }

    private fun observeProductInfo() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateProductSimilar.Success -> {
                    hideLoading()
                    setRecyclerViewProduct(data.info.products)
                }

                is StateProductSimilar.Loading -> {
                    showLoading()
                }

                is StateProductSimilar.Error -> {
                    hideLoading()
                }
            }
        }
    }

    override fun onSimilarProductItemClick(idProduct: Int) {
        val myIntent = Intent(this, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(ARG_PRODUCT_ID, idProduct)
        myIntent.putExtras(bundle)
        startActivity(myIntent)
    }

    override fun onSimilarProductFavoriteItemClick(product: Product) {
        viewModel.updateFavoriteStatus(product)
    }

    private fun observeFavoriteStatus() {
        viewModel.favoriteStatus.observe(this) { pair ->
            val (productId, isFavorite) = pair
            if (productId > 0) {
                val index = adapter.productList?.indexOfFirst { it.idProduct == productId }
                if (index != null && index != -1) {
                    adapter.productList?.get(index)?.isFavorite = isFavorite
                    adapter.notifyItemChanged(index)
                }
            }
        }
    }
}
