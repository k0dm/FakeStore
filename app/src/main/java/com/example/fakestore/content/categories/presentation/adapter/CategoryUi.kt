package com.example.fakestore.content.categories.presentation.adapter

import com.example.fakestore.databinding.ViewholderCategoryBinding
import com.example.fakestore.databinding.ViewholderErrorBinding

interface CategoryUi {

    fun type(): CategoryTypeUi

    fun showMessage(binding: ViewholderErrorBinding) = Unit

    fun showCategory(binding: ViewholderCategoryBinding) = Unit

    fun goToProducts(viewModel: CategoryAndRetryClickActions) = Unit

    object Progress : CategoryUi {

        override fun type() = CategoryTypeUi.Progress
    }

    data class Error(private val message: String) : CategoryUi {

        override fun type() = CategoryTypeUi.Error

        override fun showMessage(binding: ViewholderErrorBinding) {
            binding.errorTextView.text = message
        }
    }

    data class Base(private val category: String) : CategoryUi {

        override fun goToProducts(viewModel: CategoryAndRetryClickActions) {
            viewModel.goToProducts(category = category)
        }

        override fun type() = CategoryTypeUi.Base

        override fun showCategory(binding: ViewholderCategoryBinding) {
            binding.categoryTextView.text = category
        }
    }
}