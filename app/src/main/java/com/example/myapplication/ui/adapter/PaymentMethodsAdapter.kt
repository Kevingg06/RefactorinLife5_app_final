package com.example.myapplication.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.databinding.ItemInstallmentBinding
import com.example.myapplication.databinding.ItemPaymentMethodBinding

class PaymentMethodAdapter(private val paymentMethods: List<PaymentMethod>) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPaymentMethodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPaymentMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paymentMethod = paymentMethods[position]
        holder.binding.tvEntityHeader.text = paymentMethod.entity

        // Set dynamic icon
        val iconResId = when (paymentMethod.entity) {
            "Galicia" -> R.drawable.ic_galicia
            "Santander" -> R.drawable.ic_santander
            else -> R.drawable.ic_default
        }
        holder.binding.headerIcon.setImageResource(iconResId)

        // Set background color and text style
        if (paymentMethod.entity == "Galicia") {
            holder.binding.headerLayout.setBackgroundColor(Color.parseColor("#FF6600"))
            holder.binding.tvEntityHeader.setBackgroundResource(R.drawable.text_background_orange)
            holder.binding.tvEntityHeader.setTextColor(Color.parseColor("#000000"))
        } else {
            holder.binding.headerLayout.setBackgroundColor(Color.parseColor("#000000"))
            holder.binding.tvEntityHeader.setBackgroundResource(R.drawable.text_background_black)
            holder.binding.tvEntityHeader.setTextColor(Color.parseColor("#FFFFFF"))
        }

        // Set installments
        holder.binding.llInstallments.removeAllViews()
        paymentMethod.installments.forEach { installment ->
            val installmentBinding = ItemInstallmentBinding.inflate(LayoutInflater.from(holder.binding.llInstallments.context), holder.binding.llInstallments, false)
            installmentBinding.root.text = "${installment.quantity} cuotas: ${installment.interest}"
            holder.binding.llInstallments.addView(installmentBinding.root)
        }
    }

    override fun getItemCount(): Int = paymentMethods.size
}