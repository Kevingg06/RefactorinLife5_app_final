package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.databinding.ItemPaymentMethodBinding

class PaymentMethodsAdapter : ListAdapter<PaymentMethod, PaymentMethodsAdapter.PaymentMethodViewHolder>(PaymentMethodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val binding = ItemPaymentMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentMethodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PaymentMethodViewHolder(private val binding: ItemPaymentMethodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(paymentMethod: PaymentMethod) {
            binding.paymentMethod = paymentMethod
            binding.executePendingBindings()
        }
    }
}

class PaymentMethodDiffCallback : DiffUtil.ItemCallback<PaymentMethod>() {
    override fun areItemsTheSame(oldItem: PaymentMethod, newItem: PaymentMethod): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PaymentMethod, newItem: PaymentMethod): Boolean {
        return oldItem == newItem
    }
}