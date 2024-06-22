package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRvRecommendationsBinding
import com.squareup.picasso.Picasso

class RecommendationsAdapter(private val productList: List<Any>) : RecyclerView.Adapter<RecommendationsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_recommendations, parent, false)
        return  RecommendationsHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecommendationsHolder, position: Int) {
        productList[position]
    }
}

class  RecommendationsHolder(view: View) : RecyclerView.ViewHolder(view){
    private val binding = ItemRvRecommendationsBinding.bind(view)

    private fun render(value: String){
        /*binding.nameProduct.text = value.name
          binding.priceProduct.text = value.price
            Picasso.get().load(value).into(binding.imageProduct)

            Continuar cuando exista el model de product.


         */
    }
}