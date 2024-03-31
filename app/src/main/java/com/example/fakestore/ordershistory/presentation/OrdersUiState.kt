package com.example.fakestore.ordershistory.presentation

import com.example.fakestore.ordershistory.presentation.adapter.orders.OrderAdapter
import com.example.fakestore.ordershistory.presentation.adapter.orders.OrderUi

interface OrdersUiState {

    fun show(adapter: OrderAdapter)

    class Base(private val orders: List<OrderUi>) : OrdersUiState {
        override fun show(adapter: OrderAdapter) {
            adapter.update(orders)
        }
    }

    object NoOrders : OrdersUiState {
        override fun show(adapter: OrderAdapter) {
            adapter.update(listOf(OrderUi.Empty))
        }
    }
}