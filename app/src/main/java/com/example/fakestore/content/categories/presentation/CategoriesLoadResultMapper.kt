package com.example.fakestore.content.categories.presentation

import com.example.fakestore.core.domain.LoadResult
import javax.inject.Inject
import javax.inject.Singleton

interface CategoriesLoadResultMapper : LoadResult.Mapper<String>

@Singleton
class BaseCategoriesLoadResultMapper @Inject constructor(
    private val communication: CategoriesCommunication
) : CategoriesLoadResultMapper {

    override fun mapSuccess(items: List<String>) {
        communication.updateUi(CategoriesUiState.Success(items))
    }

    override fun mapError(message: String) {
        communication.updateUi(CategoriesUiState.Error(message))
    }
}