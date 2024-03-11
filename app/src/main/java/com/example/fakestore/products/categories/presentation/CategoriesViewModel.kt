package com.example.fakestore.products.categories.presentation

import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.ProvideLiveData
import com.example.fakestore.core.RunAsync
import com.example.fakestore.main.Navigation
import com.example.fakestore.products.categories.domain.CategoriesRepository
import com.example.fakestore.products.categories.domain.LoadCategoriesResult
import com.example.fakestore.products.categories.presentation.adapter.CategoryAndRetryClickActions
import com.example.fakestore.products.products.presentation.ProductsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val navigation: Navigation.Navigate,
    private val communication: CategoriesCommunication,
    private val repository: CategoriesRepository,
    private val mapper: LoadCategoriesResult.Mapper = BaseLoadCategoriesResultMapper(communication),
    runAsync: RunAsync
) : BaseViewModel(runAsync), CategoryAndRetryClickActions, ProvideLiveData<CategoriesUiState> {

    override fun liveData() = communication.liveData()

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
