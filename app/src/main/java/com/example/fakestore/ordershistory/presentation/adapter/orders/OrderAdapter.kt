package com.example.fakestore.ordershistory.presentation.adapter.orders

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ViewholderNoOrdersBinding
import com.example.fakestore.databinding.ViewholderOrderBinding

class OrderAdapter(
    private val types: List<OrdersTypeUi> = listOf(
        OrdersTypeUi.Base, OrdersTypeUi.Empty
    )
) : RecyclerView.Adapter<OrdersViewHolder>() {

    private val products = mutableListOf<OrderUi>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newListProducts: List<OrderUi>) {
        products.clear()
        products.addAll(newListProducts)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = types.indexOf(products[position].type())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return types[viewType].createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}

abstract class OrdersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(orderUi: OrderUi) = Unit

    class Base(private val binding: ViewholderOrderBinding) :
        OrdersViewHolder(binding.root) {

        override fun bind(orderUi: OrderUi) {
            orderUi.showOrder(binding)
        }
    }

    class Empty(binding: ViewholderNoOrdersBinding) : OrdersViewHolder(binding.root)
}