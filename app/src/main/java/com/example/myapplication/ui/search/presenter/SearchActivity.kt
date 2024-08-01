package com.example.myapplication.ui.search.presenter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.StateProductsSearch
import com.example.myapplication.data.dto.response.ProductSearch
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_STATE
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_TYPE_ID
import com.example.myapplication.databinding.ActivitySearchBinding
import com.example.myapplication.ui.adapter.SearchProductAdapter
import com.example.myapplication.ui.viewItem.presenter.activity.DetailsActivity

class SearchActivity : AppCompatActivity(), SearchProductAdapter.OnSearchProductItemClickListener {
    private var idProductType: Int = 1
    private var productName: String = ""
    private var favorite: Boolean = false
    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var adapter: SearchProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bundle = intent.extras

        bundle?.let {
            idProductType = it.getInt(ARG_PRODUCT_TYPE_ID)
            favorite = it.getBoolean(ARG_PRODUCT_STATE)

            setFavoriteData(favorite)
        }

        callFirstProducts(idProductType, favorite)

        reduceSize()
        initFavoriteIcon()
        observeFavorites()
        setupRecyclerView()
        observerSearchProduct()
        observeFavoriteStatus()


        binding.apply {
            svSearchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        productName = query
                        viewModel.searchProducts(idProductType, query, favorite)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            binding.supportMessageHome.setOnClickListener {
                sendSupportEmail()
            }
        }

    }

    private fun callFirstProducts(idProductType: Int, favorite: Boolean) {
        viewModel.searchProducts(
            idProductType,
            "",
            favorite
        )
    }

    private fun observeFavorites() {
        viewModel.isFavorite.observe(this) { isFavorite ->
            setFavoriteIcon(isFavorite)
        }
    }

    private fun observerSearchProduct() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateProductsSearch.Success -> {
                    hideLoading()
                    hideNoContent()
                    setRecyclerView(data.info.products)
                }

                is StateProductsSearch.Loading -> showLoading()


                is StateProductsSearch.Error -> {
                    hideLoading()
                    showError()
                }

                StateProductsSearch.NoContent -> {
                    hideLoading()
                    showNoContent()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = SearchProductAdapter(mutableListOf(), this, this)
        binding.rvProductsSearch.layoutManager = LinearLayoutManager(this)
        binding.rvProductsSearch.adapter = adapter
    }

    private fun setRecyclerView(
        value: MutableList<ProductSearch>?
    ) {
        runOnUiThread {
            adapter.updateData(value)
        }
    }

    private fun showLoading() {
        runOnUiThread {
            binding.loadingScreen.rlLoading.visibility = View.VISIBLE
            binding.rvProductsSearch.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        runOnUiThread {
            binding.loadingScreen.rlLoading.visibility = View.GONE
            binding.rvProductsSearch.visibility = View.VISIBLE
        }
    }

    private fun showError() {
        runOnUiThread {
            binding.rvProductsSearch.visibility = View.GONE
            binding.errorLayout.visibility = View.VISIBLE
        }
    }

    private fun showNoContent() {
        runOnUiThread {
            binding.rvProductsSearch.visibility = View.GONE
            binding.noContentLayout.visibility = View.VISIBLE
        }
    }

    private fun hideNoContent() {
        runOnUiThread {
            binding.rvProductsSearch.visibility = View.VISIBLE
            binding.noContentLayout.visibility = View.GONE
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean?) {
        favorite = isFavorite ?: false
        if (favorite) {
            binding.ivIconHeart.setImageResource(R.drawable.icon_heart_solid)
        } else {
            binding.ivIconHeart.setImageResource(R.drawable.icon_heart)
        }
    }

    private fun setFavoriteData(iconState: Boolean) {
        viewModel.setFavoriteData(iconState)
    }

    private fun initFavoriteIcon() {
        binding.ivIconHeart.setOnClickListener {
            val buttonState = viewModel.isFavorite.value ?: false
            val currentButtonState = !buttonState
            setFavoriteData(currentButtonState)
            favorite = !favorite
            viewModel.searchProducts(idProductType, productName, favorite)
        }
    }

    private fun reduceSize(){
        val searchView = findViewById<SearchView>(R.id.sv_search_product)
        val hintTextView = searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)

        hintTextView.textSize = 13f
        hintTextView.setTextColor(Color.GRAY)
    }

    override fun onSearchProductItemClick(idProduct: Int) {
        val myIntent = Intent(this, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(Constants.ARG_PRODUCT_ID, idProduct)
        myIntent.putExtras(bundle)
        startActivity(myIntent)
    }

    override fun onSearchProductFavoriteItemClick(product: ProductSearch) {
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

    private fun createEmailIntent(): Intent {
        val subject = Constants.SUPPORT_EMAIL_SUBJECT
        val email = Constants.SUPPORT_EMAIL
        val uriText = "mailto:$email?subject=${Uri.encode(subject)}"
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(uriText)
        }
    }

    private fun sendSupportEmail() {
        val emailIntent = createEmailIntent()
        startActivity(Intent.createChooser(emailIntent, "Enviar correo"))
    }
}
