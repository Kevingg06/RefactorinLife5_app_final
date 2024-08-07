package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.databinding.ItemRvRecommendationsBinding
import com.example.myapplication.ui.utils.transformPrice
import com.squareup.picasso.Picasso

class AdapterProduct(private var productList: MutableList<Product>?, private val goToDetails : (Product) -> Any) : RecyclerView.Adapter<ProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_recommendations, parent, false)
        return ProductHolder(view)

    }

    fun updateProducts(newProducts: MutableList<Product>?) {
        productList = newProducts
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = productList?.get(position)
        product?.let {
            holder.render(it, goToDetails)
        }
    }
}

class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRvRecommendationsBinding.bind(view)

    fun render(value: Product, goToDetails : (Product) -> Any) {
        val image = value.image
        val name = value.name
        val price = value.price.toString().transformPrice(value.currency?: "")
        binding.nameProduct.text = name
        binding.priceProduct.text = price
        Picasso.get()
            .load(image)
            .error(R.drawable.error_img_home)
            .into(binding.imageProduct)

        binding.root.setOnClickListener { goToDetails(value) }
    }
}
