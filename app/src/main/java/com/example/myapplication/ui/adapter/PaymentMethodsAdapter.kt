package com.example.myapplication.ui.adapter

import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.Installment
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.databinding.ItemInstallmentBinding
import com.example.myapplication.databinding.ItemPaymentMethodBinding

class PaymentMethodAdapter(private val paymentMethods: List<PaymentMethod>?, private val isError: Boolean = false) : RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_method, parent, false)
        return PaymentMethodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (isError || paymentMethods.isNullOrEmpty()) 1 else paymentMethods.size
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        if (isError || paymentMethods.isNullOrEmpty()) {
            holder.renderStatic()
        } else {
            holder.render(paymentMethods[position])
        }
    }

    class PaymentMethodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPaymentMethodBinding.bind(view)

        fun render(paymentMethod: PaymentMethod) {

            val iconResId = when (paymentMethod.entity) {
                "Galicia" -> R.drawable.ic_galicia
                "Santander" -> R.drawable.ic_santander
                else -> R.drawable.ic_default
            }
            binding.headerIcon.setImageResource(iconResId)

            binding.headerLayout.setBackgroundColor(
                when (paymentMethod.entity) {
                    "Galicia" -> Color.parseColor("#FF6600")
                    "Santander" -> Color.parseColor("#000000")
                    else -> Color.parseColor("#000000")
                }
            )

            binding.llInstallments.removeAllViews()
            paymentMethod.installments.forEach { installment ->
                val installmentBinding = ItemInstallmentBinding.inflate(LayoutInflater.from(binding.llInstallments.context), binding.llInstallments, false)
                val formattedText = "<b>${installment.quantity} cuotas:</b> ${installment.interest}"
                installmentBinding.root.text = Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.llInstallments.addView(installmentBinding.root)
            }
        }

        fun renderStatic() {
            binding.headerIcon.setImageResource(R.drawable.ic_default)
            binding.headerLayout.setBackgroundColor(Color.parseColor("#000000"))

            val staticInstallments = listOf(
                Installment(3, "sin interes"),
                Installment(6, "Sin interes"),
                Installment(9, "fijas"),
                Installment(12, "fijas")
            )

            binding.llInstallments.removeAllViews()
            staticInstallments.forEach { installment ->
                val installmentBinding = ItemInstallmentBinding.inflate(
                    LayoutInflater.from(binding.llInstallments.context),
                    binding.llInstallments,
                    false
                )
                val formattedText = "<b>${installment.quantity} cuotas:</b> ${installment.interest}"
                installmentBinding.root.text = Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.llInstallments.addView(installmentBinding.root)
            }
        }
    }
}
