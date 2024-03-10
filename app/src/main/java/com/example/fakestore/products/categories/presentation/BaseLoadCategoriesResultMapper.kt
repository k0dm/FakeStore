package com.example.fakestore.products.categories.presentation

import com.example.fakestore.core.domain.LoadResult

class BaseLoadCategoriesResultMapper(
    private val communication: CategoriesCommunication
) : LoadResult.Mapper<String> {

    override fun mapSuccess(categories: List<String>) {
        communication.updateUi(CategoriesUiState.Success(categories))
    }

    override fun mapError(message: String) {
        communication.updateUi(CategoriesUiState.Error(message))
    }
}