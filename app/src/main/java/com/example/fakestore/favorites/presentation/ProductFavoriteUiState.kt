package com.example.fakestore.favorites.presentation

import com.example.fakestore.favorites.presentation.adapter.ProductFavoriteUi
import com.example.fakestore.favorites.presentation.adapter.ProductsFavoritesAdapter

interface ProductFavoriteUiState {

    fun show(adapter: ProductsFavoritesAdapter)

    data class Success(private val products: List<ProductFavoriteUi>) : ProductFavoriteUiState {
        override fun show(adapter: ProductsFavoritesAdapter) {
            adapter.update(products)
        }
    }

    object NoFavorites : ProductFavoriteUiState {
        override fun show(adapter: ProductsFavoritesAdapter) {
            adapter.update(listOf(ProductFavoriteUi.NoFavorites))
        }
    }
}