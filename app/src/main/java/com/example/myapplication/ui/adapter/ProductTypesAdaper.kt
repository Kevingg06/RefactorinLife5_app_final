package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.ProductType
import com.example.myapplication.data.dto.response.ProductTypesResponse
import com.example.myapplication.databinding.ItemRvCategoriesHomeBinding

//class ProductTypesAdapter(private val productTypesList: ProductTypesResponse) : RecyclerView.Adapter<ProductTypeHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTypeHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_categories_home,parent,false)
//        return  ProductTypeHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return productTypesList.productTypes?.size ?: 0
//    }
//
//    override fun onBindViewHolder(holder: ProductTypeHolder, position: Int) {
//        productTypesList.productTypes?.get(position)?.let { it.description?.let { it1 ->
//            holder.render(
//                it1
//            )
//        } }
//    }
//}
//
//class ProductTypeHolder(view: View) : RecyclerView.ViewHolder(view) {
//    private val binding = ItemRvCategoriesHomeBinding.bind(view)
//    fun render(value : String){
//        binding.categoryName.text = value
//
//        itemView.setOnClickListener {
//
//        }
//    }
//}

class ProductTypesAdapter(
    private val productTypesList: ProductTypesResponse,
    private val onCategoryClick: (ProductType) -> Unit
) : RecyclerView.Adapter<ProductTypeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductTypeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_categories_home, parent, false)
        return ProductTypeHolder(view, onCategoryClick)
    }

    override fun getItemCount(): Int {
        return productTypesList.productTypes?.size ?: 0
    }

    override fun onBindViewHolder(holder: ProductTypeHolder, position: Int) {
        productTypesList.productTypes?.get(position)?.let { productType ->
            holder.bind(productType)
        }
    }
}

class ProductTypeHolder(view: View, private val onCategoryClick: (ProductType) -> Unit) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRvCategoriesHomeBinding.bind(view)

    fun bind(productType: ProductType) {
        binding.categoryName.text = productType.description

        itemView.setOnClickListener {
            onCategoryClick(productType)
        }
    }
}
