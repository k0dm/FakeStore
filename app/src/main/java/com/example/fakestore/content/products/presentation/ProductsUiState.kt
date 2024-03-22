package com.example.fakestore.content.products.presentation

import com.example.fakestore.content.products.presentation.adapter.ProductUi
import com.example.fakestore.content.products.presentation.adapter.ProductsAdapter

interface ProductsUiState {

    fun show(adapter: ProductsAdapter)

    data class Error(private val message: String) : ProductsUiState {
        override fun show(adapter: ProductsAdapter) {
            adapter.update(listOf(ProductUi.Error(message)))
        }
    }

    data class Success(private val products: List<ProductUi>) : ProductsUiState {
        override fun show(adapter: ProductsAdapter) {
            adapter.update(products)
        }
    }

    object Progress : ProductsUiState {
        override fun show(adapter: ProductsAdapter) {
            adapter.update(listOf(ProductUi.Progress))
        }
    }

    object Empty : ProductsUiState {
        override fun show(adapter: ProductsAdapter) = Unit
    }
}