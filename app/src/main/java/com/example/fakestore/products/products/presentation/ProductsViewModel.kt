package com.example.fakestore.products.products.presentation

import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.ProvideLiveData
import com.example.fakestore.core.RunAsync
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.main.Navigation
import com.example.fakestore.main.Screen
import com.example.fakestore.products.products.domain.ProductItem
import com.example.fakestore.products.products.domain.ProductsRepository
import com.example.fakestore.products.products.presentation.adapter.ProductAndRetryClickActions
import com.example.fakestore.products.products.presentation.adapter.ProductUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val navigation: Navigation.Navigate,
    private val communication: ProductsCommunication,
    private val repository: ProductsRepository,
    itemUiMapper: ProductItem.Mapper<ProductUi> = ProductItemToProductUiMapper(),
    private val mapper: LoadResult.Mapper<ProductItem> = BaseProductsLoadResultMapper(
        communication,
        itemUiMapper
    ),
    runAsync: RunAsync,
) : BaseViewModel(runAsync), ProductAndRetryClickActions, ProvideLiveData<ProductsUiState> {

    override fun liveData() = communication.liveData()

    fun init(category: String) {
        communication.updateUi(ProductsUiState.Progress)
        runAsync({
            repository.products(category)
        }, { result ->
            result.map(mapper)
        })
    }

    override fun retry(category: String) {
        init(category)
    }

    override fun goToProductsDetails(id: Int, category: String) {
        navigation.updateUi(ProductDetailsScreen(id, category))
    }

    override fun changeAddedToCart(id: Int) {
        runAsync({
            repository.changeAddedToCart(id)
        }) {

        }
    }

    override fun changeAddedToFavorites(id: Int) {
        runAsync({
            repository.changeFavorite(id)
        }) {}
    }

    fun goToCategories() {
        navigation.updateUi(Screen.Pop)
    }

}
