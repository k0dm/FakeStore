package com.example.fakestore.products.categories.presentation

import com.example.fakestore.products.categories.presentation.adapter.CategoriesAdapter
import com.example.fakestore.products.categories.presentation.adapter.CategoryUi

interface CategoriesUiState {

    fun show(adapter: CategoriesAdapter)

    data class Error(private val message: String) : CategoriesUiState {
        override fun show(adapter: CategoriesAdapter) {
            adapter.update(listOf(CategoryUi.Error(message)))
        }
    }

    data class Success(private val categories: List<String>) : CategoriesUiState {
        override fun show(adapter: CategoriesAdapter) {
            adapter.update(categories.map { CategoryUi.Base(it) })
        }
    }

    object Progress : CategoriesUiState {
        override fun show(adapter: CategoriesAdapter) {
            adapter.update(listOf(CategoryUi.Progress))
        }
    }

    object Empty : CategoriesUiState {
        override fun show(adapter: CategoriesAdapter) = Unit
    }
}