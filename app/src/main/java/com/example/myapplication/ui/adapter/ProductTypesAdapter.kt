package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.ProductType
import com.example.myapplication.databinding.ItemRvCategoriesHomeBinding

class ProductTypesAdapter(
    private val productTypes: MutableList<ProductType>?,
    private val listener: OnCategoryClickListener
) : RecyclerView.Adapter<ProductTypeHolder>() {
    interface OnCategoryClickListener {
        fun onCategoryClick(category: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTypeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_categories_home, parent, false)
        return ProductTypeHolder(view)
    }

    override fun getItemCount(): Int {
        return productTypes?.size ?: 0
    }

    override fun onBindViewHolder(holder: ProductTypeHolder, position: Int) {
        productTypes?.get(position)?.let {
            it.let { it1 ->
                holder.render(
                    it1, listener
                )
            }
        }
    }
}

class ProductTypeHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRvCategoriesHomeBinding.bind(view)
    fun render(value: ProductType, listener: ProductTypesAdapter.OnCategoryClickListener) {
        binding.categoryName.text = value.description

        itemView.setOnClickListener {
            value.idProductType?.let { it1 -> listener.onCategoryClick(it1) }
        }
    }
}
