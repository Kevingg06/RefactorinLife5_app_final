package com.example.myapplication.ui.home.presenter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import com.example.myapplication.ui.adapter.ProductTypesAdapter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.dto.dataSource.getToken
import com.example.myapplication.data.dto.model.StateProduct
import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.data.dto.response.ProductType
import com.example.myapplication.data.dto.response.SingleProductResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_ID
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_STATE
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_TYPE_ID
import com.example.myapplication.data.utils.TokenHolder.savedToken
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.ui.adapter.AdapterProduct
import com.example.myapplication.ui.utils.transformPrice
import com.example.myapplication.ui.search.presenter.SearchActivity
import com.example.myapplication.ui.viewItem.presenter.activity.DetailsActivity
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity(), ProductTypesAdapter.OnCategoryClickListener {
    private val viewModel by viewModels<HomeViewModel>()
    private var idMainProduct: Int? = null
    private var idProductType: Int = 1
    private var favorite: Boolean = false
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
        reduceSize()
        initFavoriteIcon()
        setupRecyclerViews()
        getHomeInfo()
        observerHomeInfo()
        observeFavoriteHeader()
        initFavoriteIconHeader()
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
            bundle.putInt(ARG_PRODUCT_ID, idMainProduct ?: -1)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
        }

        binding.clickableOverlay.setOnClickListener {
            val myIntent = Intent(this, SearchActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(ARG_PRODUCT_TYPE_ID, idProductType)
            bundle.putBoolean(ARG_PRODUCT_STATE, favorite)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
        }

        binding.supportMessageHome.setOnClickListener {
            sendSupportEmail()
        }
    }

    private fun reduceSize(){
        val searchView = findViewById<SearchView>(R.id.sv_home)
        val hintTextView = searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)

        hintTextView.textSize = 13f
        hintTextView.setTextColor(Color.GRAY)
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

    private fun setFavorite(id: Int) {
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

    private fun observeFavoriteHeader() {
        viewModel.isFavoriteHeader.observe(this) { isFavorite ->
            setFavoriteIconHeader(isFavorite)
        }
    }

    private fun setFavoriteData(iconState: Boolean) {
        viewModel.setFavoriteData(iconState)
    }

    private fun setFavoriteDataHeader(iconState: Boolean) {
        viewModel.setFavoriteDataHeader(iconState)
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

    private fun initFavoriteIconHeader() {
        binding.ivIconHeart.setOnClickListener {
            val buttonState = viewModel.isFavoriteHeader.value ?: false
            val currentButtonState = !buttonState
            setFavoriteDataHeader(currentButtonState)
            favorite = !favorite

            val myIntent = Intent(this, SearchActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(ARG_PRODUCT_TYPE_ID, idProductType)
            bundle.putBoolean(ARG_PRODUCT_STATE, favorite)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
        }
    }

    private fun setRecyclerViewProduct(value: MutableList<Product>?) {
        runOnUiThread {
            productsAdapter = AdapterProduct(value, goToDetails = { goToDetails(it) })
            binding.rvRecommendationsHome.adapter = productsAdapter
        }
    }

    private fun goToDetails(item: Product) {
        val myIntent = Intent(this, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(ARG_PRODUCT_ID, item.idProduct ?: -1)
        myIntent.putExtras(bundle)
        startActivity(myIntent)
    }

    private fun setFavoriteIconHeader(isFavorite: Boolean?) {
        favorite = isFavorite ?: false
        if (favorite) {
            binding.ivIconHeart.setImageResource(R.drawable.icon_heart_solid)
        } else {
            binding.ivIconHeart.setImageResource(R.drawable.icon_heart)
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

    private fun setProductDailyOffer(product: SingleProductResponse) {
        runOnUiThread {
            binding.tvStateProduct.text = Constants.DAILY_OFFER_STATE
            binding.productName.text = product.name
            binding.productDescription.text = product.description
            binding.productPrice.text = product.price.toString().transformPrice(product.currency?: "")

            setDailyOfferTitle(product.dailyOffer)
            
            if (!product.images.isNullOrEmpty())
                Picasso.get().load(product.images[0].link)
                    .into(binding.imageMainProduct)

            product.isFavorite?.let {
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

    private fun setDailyOfferTitle(isDailyOffer: Boolean?) {
        val dailyOffer = isDailyOffer ?: false
        if (dailyOffer) {
            binding.tvStateProduct.text = getString(R.string.daily_offer_title)
        } else {
            binding.tvStateProduct.text = getString(R.string.last_visited_title)
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
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_message)))
    }
}
