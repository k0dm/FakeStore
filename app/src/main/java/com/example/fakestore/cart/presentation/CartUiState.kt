package com.example.fakestore.cart.presentation

import com.example.fakestore.cart.presentation.adapter.ProductsCartAdapter

interface CartUiState {

    fun show(adapter: ProductsCartAdapter)

    object NoCarts : CartUiState {
        override fun show(adapter: ProductsCartAdapter) {
            adapter.update(listOf(ProductCartUi.NoCarts))
        }
    }

    class Base(private val products: List<ProductCartUi>) : CartUiState {
        override fun show(adapter: ProductsCartAdapter) {
            adapter.update(products)
        }
    }
}