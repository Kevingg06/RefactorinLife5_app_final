package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.Installment
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.databinding.ItemPaymentMethodBinding
import com.example.myapplication.databinding.ItemTableBodyBinding
import com.example.myapplication.databinding.ItemTableHeaderBinding

//class PaymentMethodsAdapter(private val paymentMethods: List<PaymentMethod>) :
//    RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_payment_method, parent, false)
//        return PaymentMethodViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
//        holder.bind(paymentMethods[position])
//    }
//
//    override fun getItemCount(): Int = paymentMethods.size
//
//    inner class PaymentMethodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val binding = ItemPaymentMethodBinding.bind(view)
//
//        fun bind(paymentMethod: PaymentMethod) {
//            binding.tvEntity.text = paymentMethod.entity
//            val installments = paymentMethod.installments.joinToString(separator = "\n") {
//                "Cantidad: ${it.quantity}\nInterés: ${it.interest}"
//            }
//            binding.tvInstallments.text = installments
//        }
//    }
//}

class PaymentMethodsAdapter(
    private val paymentMethods: List<PaymentMethod>
) : RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodsViewHolder>() {

    class PaymentMethodsViewHolder(
        private val headerBinding: ItemTableHeaderBinding,
        private val bodyBinding: ItemTableBodyBinding
    ) : RecyclerView.ViewHolder(headerBinding.root) {

        fun bind(paymentMethod: PaymentMethod, isHeader: Boolean) {
            if (isHeader) {
                headerBinding.tvEntityHeader.text = paymentMethod.entity
                headerBinding.root.setBackgroundColor(
                    if (paymentMethod.entity == "Galicia") android.graphics.Color.parseColor("#FF9800")
                    else android.graphics.Color.parseColor("#000000")
                )
            } else {
                val installmentsText = paymentMethod.installments.joinToString(separator = "\n") {
                    "${it.quantity} cuotas: ${it.interest}"
                }
                bodyBinding.tvInstallments.text = installmentsText
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val headerBinding = ItemTableHeaderBinding.inflate(inflater, parent, false)
        val bodyBinding = ItemTableBodyBinding.inflate(inflater, parent, false)
        return PaymentMethodsViewHolder(headerBinding, bodyBinding)
    }

    override fun onBindViewHolder(holder: PaymentMethodsViewHolder, position: Int) {
        val paymentMethod = paymentMethods[position]
        val isHeader = position % 2 == 0
        holder.bind(paymentMethod, isHeader)
    }

    override fun getItemCount(): Int = paymentMethods.size
}