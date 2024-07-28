package com.example.myapplication.ui.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.PaymentMethod
import com.example.myapplication.databinding.ItemInstallmentBinding
import com.example.myapplication.databinding.ItemPaymentMethodBinding

class PaymentMethodAdapter(private val paymentMethods: List<PaymentMethod>) : RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_method, parent, false)
        return PaymentMethodViewHolder(view)
    }

    override fun getItemCount(): Int = paymentMethods.size

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        holder.render(paymentMethods[position])
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

            when (paymentMethod.entity) {
                "Galicia" -> {
                    binding.headerLayout.setBackgroundColor(Color.parseColor("#FF6600"))
                }
                "Santander" -> {
                    binding.headerLayout.setBackgroundColor(Color.parseColor("#000000"))
                }
                "Otros" -> {
                    val colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                    binding.headerIcon.colorFilter = colorFilter

                    val layoutParams = binding.headerIcon.layoutParams as ViewGroup.LayoutParams
                    layoutParams.width = 100
                    binding.headerIcon.layoutParams = layoutParams

                    binding.headerLayout.setBackgroundColor(Color.parseColor("#000000"))
                }
                else -> {
                    binding.headerLayout.setBackgroundColor(Color.parseColor("#000000"))
                }
            }

            binding.llInstallments.removeAllViews()
            paymentMethod.installments.forEach { installment ->
                val installmentBinding = ItemInstallmentBinding.inflate(LayoutInflater.from(binding.llInstallments.context), binding.llInstallments, false)
                val formattedText = "<b>${installment.quantity} cuotas:</b> ${installment.interest}"
                installmentBinding.root.text = Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY)
                binding.llInstallments.addView(installmentBinding.root)
            }
        }
    }
}
