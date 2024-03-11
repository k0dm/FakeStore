package com.example.fakestore.products.categories.presentation

import com.example.fakestore.products.categories.domain.LoadCategoriesResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseLoadCategoriesResultMapper @Inject constructor(
    private val communication: CategoriesCommunication
) : LoadCategoriesResult.Mapper {

    override fun mapSuccess(categories: List<String>) {
        communication.updateUi(CategoriesUiState.Success(categories))
    }

    override fun mapError(message: String) {
        communication.updateUi(CategoriesUiState.Error(message))
    }
}