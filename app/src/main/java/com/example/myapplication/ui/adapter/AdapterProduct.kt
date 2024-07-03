package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.data.dto.response.ProductResponse
import com.example.myapplication.databinding.ItemRvRecommendationsBinding
import com.squareup.picasso.Picasso

class AdapterProduct(private val productList: ProductResponse) : RecyclerView.Adapter<ProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_recommendations, parent, false)
        return ProductHolder(view)

    }

    override fun getItemCount(): Int {
        return productList.products?.size ?: 0
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        //holder.render(productList[position])
        val product = productList.products?.get(position)
        product?.let {
            holder.render(it)
        }
    }
}

class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRvRecommendationsBinding.bind(view)

    fun render(value: Product) {
        val image = value.image
        val name = value.name
        val price = value.price
        Picasso.get().load(image).into(binding.imageProduct)
        binding.nameProduct.text = name
        binding.priceProduct.text = price
    }
}
