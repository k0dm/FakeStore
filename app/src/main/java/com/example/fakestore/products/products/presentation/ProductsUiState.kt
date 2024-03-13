package com.example.fakestore.products.products.presentation

import com.example.fakestore.products.products.presentation.adapter.ProductUi

interface ProductsUiState {

    data class Error(private val message: String) : ProductsUiState

    data class Success(private val products: List<ProductUi>) : ProductsUiState

    object Progress : ProductsUiState

    object Empty : ProductsUiState
}