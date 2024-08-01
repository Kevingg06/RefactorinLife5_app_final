package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.Image
import com.example.myapplication.data.dto.response.ProductByIdResponse
import com.example.myapplication.databinding.ItemImagesBinding
import com.squareup.picasso.Picasso

class ProductImagesAdapter(private val product: ProductByIdResponse) :
    RecyclerView.Adapter<ImagesHolder>() {

    private val images = product.images ?: listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
        return ImagesHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImagesHolder, position: Int) {
        holder.render(images[position], product)
    }
}

class ImagesHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemImagesBinding.bind(view)

    private fun setFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ivAddFavoritesDetails.setImageResource(R.drawable.icon_favorite_solid)
        } else {
            binding.ivAddFavoritesDetails.setImageResource(R.drawable.icon_favorite)
        }
    }

    fun render(value: Image, product: ProductByIdResponse) {

        setFavorite(product.isFavorite ?: false)
        Picasso.get()
            .load(value.link)
            .error(R.drawable.error_img)
            .into(binding.imageProductDetails)
    }
}
