package com.example.fakestore.cart.presentation

import com.example.fakestore.R
import com.example.fakestore.cart.presentation.adapter.ProductCartActions
import com.example.fakestore.cart.presentation.adapter.ProductCartTypeUi
import com.example.fakestore.cart.presentation.adapter.ProductsCartAdapter
import com.example.fakestore.databinding.ViewholderCartProductBinding
import com.example.fakestore.databinding.ViewholderNoCartBinding
import com.squareup.picasso.Picasso

interface ProductCartUi {

    fun isTheSameById(id: Int): Boolean = false

    fun type(): ProductCartTypeUi

    fun id(): Int = -1

    fun goToProductsDetails(viewModel: ProductCartActions) = Unit

    fun showNoCarts(binding: ViewholderNoCartBinding) = Unit

    fun showCartsProducts(binding: ViewholderCartProductBinding, adapter: ProductsCartAdapter) =
        Unit

    fun removeCartProduct(viewModel: ProductCartActions) = Unit

    fun changeAddQuantity(viewModel: ProductCartActions) = Unit

    fun changeRemoveQuantity(viewModel: ProductCartActions) = Unit

    class Base(
        private val id: Int,
        private val title: String,
        private val price: Double,
        private val imageUrl: String,
        private var quantity: Int
    ) : ProductCartUi {

        override fun removeCartProduct(viewModel: ProductCartActions) {
            viewModel.removeFromCart(id)
        }

        override fun changeAddQuantity(viewModel: ProductCartActions) {
            viewModel.changeQuantity(id, ++quantity)
        }

        override fun changeRemoveQuantity(viewModel: ProductCartActions) {
            if (quantity > 1) {
                viewModel.changeQuantity(id, --quantity)
            }
        }

        override fun type(): ProductCartTypeUi = ProductCartTypeUi.Base

        override fun goToProductsDetails(viewModel: ProductCartActions) {
            viewModel.goToDetails(id = id)
        }

        override fun showCartsProducts(
            binding: ViewholderCartProductBinding,
            adapter: ProductsCartAdapter
        ) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.products)
                .into(binding.imageProduct);

            binding.titleTextView.text = title
            binding.priceTextView.text = price.toString()
            binding.quantityTextView.text = quantity.toString()
        }
    }

    object NoCarts : ProductCartUi {

        override fun type() = ProductCartTypeUi.NoCarts

        override fun showNoCarts(binding: ViewholderNoCartBinding) {
            binding.textView.setText(R.string.no_added_items_in_a_cart) // No added items in a cart
        }
    }

}