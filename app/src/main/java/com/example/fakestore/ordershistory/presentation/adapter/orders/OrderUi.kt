package com.example.fakestore.ordershistory.presentation.adapter.orders

import com.example.fakestore.databinding.ViewholderOrderBinding
import com.example.fakestore.ordershistory.domain.OrderProduct
import java.util.Locale

interface OrderUi {

    fun type(): OrdersTypeUi

    fun showOrder(binding: ViewholderOrderBinding) = Unit

    class Base(
        val number: Int,
        val date: String,
        val totalPrice: Double,
        val products: List<OrderProduct>
    ) : OrderUi {
        override fun type(): OrdersTypeUi = OrdersTypeUi.Base

        override fun showOrder(binding: ViewholderOrderBinding) = with(binding) {
            totalPriceTextView.text = String.format(Locale.ENGLISH, "%.2f", totalPrice)
            orderNumberTextView.text = number.toString()
            dateTextView.text = date

            productItemAdapter.adapter = ProductItemOrderAdapter(products)
        }
    }

    object Empty : OrderUi {
        override fun type(): OrdersTypeUi = OrdersTypeUi.Empty
    }
}