package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemRvCategoriesHomeBinding

class CategoriesAdapter(private val categoriesList: List<String>) :
    RecyclerView.Adapter<CategoriesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_categories_home, parent, false)
        return CategoriesHolder(view)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        categoriesList[position]
    }
}

class CategoriesHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemRvCategoriesHomeBinding.bind(view)

    private fun render(value: String) {
        binding.categoryName.text = value
    }
}
