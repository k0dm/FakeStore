package com.example.fakestore.ordershistory.presentation.adapter.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fakestore.databinding.ViewholderNoOrdersBinding
import com.example.fakestore.databinding.ViewholderOrderBinding

interface OrdersTypeUi {

    fun createViewHolder(parent: ViewGroup): OrdersViewHolder

    object Base : OrdersTypeUi {
        override fun createViewHolder(parent: ViewGroup): OrdersViewHolder =
            OrdersViewHolder.Base(
                ViewholderOrderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object Empty : OrdersTypeUi {
        override fun createViewHolder(parent: ViewGroup): OrdersViewHolder =
            OrdersViewHolder.Empty(
                ViewholderNoOrdersBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}