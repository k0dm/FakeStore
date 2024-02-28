package com.example.fakestore.products.categories.presentation

import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.LiveDataWrapper
import com.example.fakestore.core.RunAsync
import com.example.fakestore.main.Navigation
import com.example.fakestore.products.categories.presentation.adapter.CategoryAndRetryClickActions
import com.example.fakestore.products.products.presentation.ProductsScreen

class CategoriesViewModel(
    private val navigation: Navigation.Navigate,
    private val communication: CategoriesCommunication,
    private val repository: CategoriesRepository,
    private val mapper: LoadCategoriesResult.Mapper = BaseLoadCategoriesResultMapper(communication),
    runAsync: RunAsync
) : BaseViewModel(runAsync), CategoryAndRetryClickActions {

    fun init() {
        communication.updateUi(CategoriesUiState.Progress)
        runAsync({
            repository.loadCategories()
        }, { result ->
            result.map(mapper)
        })
    }

    override fun retry() {
        init()
    }

    override fun goToProducts(category: String) {
        navigation.updateUi(ProductsScreen(category))
    }
}

interface CategoriesRepository {

    suspend fun loadCategories(): LoadCategoriesResult
}

interface CategoriesCommunication : LiveDataWrapper<CategoriesUiState>

interface CategoriesUiState {

    data class Error(private val message: String) : CategoriesUiState

    data class Success(private val categories: List<String>) : CategoriesUiState

    object Progress : CategoriesUiState

    object Empty : CategoriesUiState
}

interface LoadCategoriesResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(categories: List<String>)

        fun mapError(message: String)
    }

    data class Success(private val categories: List<String>) : LoadCategoriesResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(categories)
        }
    }

    data class Error(private val message: String) : LoadCategoriesResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }
}

class BaseLoadCategoriesResultMapper(
    private val communication: CategoriesCommunication
) : LoadCategoriesResult.Mapper {
    override fun mapSuccess(categories: List<String>) {
        communication.updateUi(CategoriesUiState.Success(categories))
    }

    override fun mapError(message: String) {
        communication.updateUi(CategoriesUiState.Error(message))
    }
}