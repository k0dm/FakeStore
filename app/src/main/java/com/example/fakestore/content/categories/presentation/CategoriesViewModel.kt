package com.example.fakestore.content.categories.presentation

import com.example.fakestore.content.categories.domain.CategoriesRepository
import com.example.fakestore.content.categories.presentation.adapter.CategoryAndRetryClickActions
import com.example.fakestore.content.products.presentation.ProductsScreen
import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.presentation.ProvideLiveData
import com.example.fakestore.core.presentation.RunAsync
import com.example.fakestore.main.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val navigation: Navigation.Navigate,
    private val communication: CategoriesCommunication,
    private val repository: CategoriesRepository,
    private val mapper: CategoriesLoadResultMapper = BaseCategoriesLoadResultMapper(communication),
    runAsync: RunAsync
) : BaseViewModel(runAsync), CategoryAndRetryClickActions, ProvideLiveData<CategoriesUiState> {

    override fun liveData() = communication.liveData()

    fun init() {
        communication.updateUi(CategoriesUiState.Progress)
        runAsync({
            repository.categories()
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

