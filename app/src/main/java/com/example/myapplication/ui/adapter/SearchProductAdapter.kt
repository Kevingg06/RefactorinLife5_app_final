package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.ProductSearch
import com.example.myapplication.databinding.ItemRvSearchBinding
import com.example.myapplication.ui.utils.transformPrice
import com.squareup.picasso.Picasso

class SearchProductAdapter(
    private var productList: MutableList<ProductSearch>?,
    private val listener: OnSearchProductItemClickListener
) : RecyclerView.Adapter<SearchProductHolder>() {

    interface OnSearchProductItemClickListener {
        fun OnSearchProductItemClick(idProduct: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_search, parent, false)
        return SearchProductHolder(view)
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SearchProductHolder, position: Int) {
        val product = productList?.get(position)
        product?.let {
            holder.render(it, listener)
        }
    }

    fun updateData(newProductList: MutableList<ProductSearch>?) {
        productList?.clear()
        if (newProductList != null) {
            productList?.addAll(newProductList)
        }
        notifyDataSetChanged()
    }
}

class SearchProductHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRvSearchBinding.bind(view)

    fun render(value: ProductSearch, listener: SearchProductAdapter.OnSearchProductItemClickListener) {
        val image = value.image
        val name = value.name
        val description = value.description
        val price = value.price.toString().transformPrice(value.currency?: "")
        val idProduct = value.idProduct
        val favorite = value.isFavorite
        Picasso.get().load(image).into(binding.itemProductImage)
        binding.itemProductBrand.text = name
        binding.productDescription.text = description
        binding.itemProductPrice.text = price

        if (favorite!!) {
            binding.itemIvAddFavorites.setImageResource(R.drawable.icon_favorite_solid)
        } else {
            binding.itemIvAddFavorites.setImageResource(R.drawable.icon_favorite)
        }

        binding.seeProductButton.setOnClickListener {
            idProduct?.let {
                listener.OnSearchProductItemClick(it)
            }
        }
    }
}
