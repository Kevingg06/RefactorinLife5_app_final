package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.Product
import com.example.myapplication.data.dto.response.ProductSearch
import com.example.myapplication.databinding.ItemRvSearchBinding
import com.example.myapplication.ui.utils.transformPrice
import com.squareup.picasso.Picasso

class SimilarProductSearch(
    var productList: MutableList<Product>?,
    private val listener: OnSimilarProductItemClickListener,
    private val listenerFavorite: OnSimilarProductItemClickListener
) : RecyclerView.Adapter<SimilarSearchProductHolder>() {

    interface OnSimilarProductItemClickListener {
        fun onSimilarProductItemClick(idProduct: Int)
        fun onSimilarProductFavoriteItemClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarSearchProductHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_search, parent, false)
        return SimilarSearchProductHolder(view)
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SimilarSearchProductHolder, position: Int) {
        val product = productList?.get(position)
        product?.let {
            holder.render(it, listener, listenerFavorite)
        }
    }

    fun updateData(newProductList: MutableList<Product>?) {
        productList?.clear()
        if (newProductList != null) {
            productList?.addAll(newProductList)
        }
        notifyDataSetChanged()
    }
}

class SimilarSearchProductHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRvSearchBinding.bind(view)

    fun render(
        value: Product,
        listener: SimilarProductSearch.OnSimilarProductItemClickListener,
        listenerFavorite: SimilarProductSearch.OnSimilarProductItemClickListener
    ) {
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

        updateFavoriteIcon(favorite)

        binding.itemIvAddFavorites.setOnClickListener {
            listenerFavorite.onSimilarProductFavoriteItemClick(value)
        }

        binding.seeProductButton.setOnClickListener {
            idProduct?.let {
                listener.onSimilarProductItemClick(it)
            }
        }
    }

    private fun updateFavoriteIcon(favorite: Boolean?) {
        if (favorite == true) {
            binding.itemIvAddFavorites.setImageResource(R.drawable.icon_favorite_solid)
        } else {
            binding.itemIvAddFavorites.setImageResource(R.drawable.icon_favorite)
        }
    }
}
