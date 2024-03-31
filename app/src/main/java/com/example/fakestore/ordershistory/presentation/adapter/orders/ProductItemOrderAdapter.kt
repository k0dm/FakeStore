package com.example.fakestore.ordershistory.presentation.adapter.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.R
import com.example.fakestore.databinding.ViewholderOrderItemBinding
import com.example.fakestore.ordershistory.domain.OrderProduct
import com.squareup.picasso.Picasso
import java.util.Locale

class ProductItemOrderAdapter(
    private val products: List<OrderProduct>
) : RecyclerView.Adapter<ProductItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductItemViewHolder(
        ViewholderOrderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.show(products[position])
    }
}

class ProductItemViewHolder(private val binding: ViewholderOrderItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun show(product: OrderProduct) {
        Picasso.get()
            .load(product.imageUrl)
            .placeholder(R.drawable.products)
            .into(binding.productImageView);

        binding.priceTextView.text = String.format(Locale.ENGLISH, "%.2f", product.price)
        binding.countNumberTextView.text = product.count.toString()
        binding.titleTextView.text = product.name
    }
}