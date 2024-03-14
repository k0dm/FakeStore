package com.example.fakestore.products.products.presentation

import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.ProvideLiveData
import com.example.fakestore.core.RunAsync
import com.example.fakestore.main.Navigation
import com.example.fakestore.main.Screen
import com.example.fakestore.products.products.domain.ProductsRepository

class ProductsViewModel(
    private val navigation: Navigation.Navigate,
    private val communication: ProductsCommunication,
    private val repository: ProductsRepository,
    itemUiMapper: ProductItemToProductUiMapper = ProductItemToProductUiMapper(),
    private val mapper: ProductsLoadResultMapper = BaseProductsLoadResultMapper(
        communication,
        itemUiMapper
    ),
    runAsync: RunAsync,
) : BaseViewModel(runAsync), ProvideLiveData<ProductsUiState> {

    override fun liveData() = communication.liveData()

    fun init(category: String) {
        communication.updateUi(ProductsUiState.Progress)
        runAsync({
            repository.products(category)
        }, { result ->
            result.map(mapper)
        })
    }

    fun retry(category: String) {
        init(category)
    }

    fun goToCategories() {
        navigation.updateUi(Screen.Pop)
    }

    fun openDetails(productId: Int) {
        navigation.updateUi(ProductDetailsScreen(productId))
    }

}
