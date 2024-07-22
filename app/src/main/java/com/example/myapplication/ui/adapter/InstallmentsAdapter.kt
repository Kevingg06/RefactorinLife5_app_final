package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.model.PaymentMethod

class InstallmentsAdapter(private val paymentMethods: List<PaymentMethod>) :
    RecyclerView.Adapter<InstallmentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_method, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paymentMethod = paymentMethods[position]
        holder.bind(paymentMethod)
    }

    override fun getItemCount(): Int {
        return paymentMethods.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(paymentMethod: PaymentMethod) {
            // Aquí debes bindear la información de paymentMethod al layout
            // Por ejemplo:
//            itemView.findViewById<TextView>(R.id.textViewHeader).text = paymentMethod.entity
            // Configura un sub-adaptador para las cuotas aquí si es necesario
        }
    }
}