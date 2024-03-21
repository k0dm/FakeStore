package com.example.fakestore.content.products.presentation

import com.example.fakestore.content.details.presentation.ProductDetailsScreen
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.content.products.domain.ProductsRepository
import com.example.fakestore.content.products.presentation.adapter.ProductAndRetryClickActions
import com.example.fakestore.content.products.presentation.adapter.ProductUi
import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.core.presentation.ProvideLiveData
import com.example.fakestore.core.presentation.RunAsync
import com.example.fakestore.main.CartBadgeLiveDataWrapper
import com.example.fakestore.main.Navigation
import com.example.fakestore.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val navigation: Navigation.Navigate,
    private val communication: ProductsCommunication,
    private val productUiCommunication: ProductCommunication,
    private val cartBadgeLiveDataWrapper: CartBadgeLiveDataWrapper.Update,
    private val productPositionLiveDataWrapper: ProductPositionLiveDataWrapper.Provide,
    private val repository: ProductsRepository,
    private val mapper: LoadResult.Mapper<ProductItem>,
    private val productItemToProductUiMapper: ProductItem.Mapper<ProductUi>,
    runAsync: RunAsync,
) : BaseViewModel(runAsync), ProductAndRetryClickActions, ProvideLiveData<ProductsUiState> {

    override fun liveData() = communication.liveData()

    fun productPositionLiveData() = productPositionLiveDataWrapper.liveData()

    fun productUiLiveData() = productUiCommunication.liveData()

    fun init(category: String) {
        communication.updateUi(ProductsUiState.Progress)
        runAsync({
            repository.products(category)
        }, { result ->
            result.map(mapper)
        })
    }

    fun product(id: Int) {
        runAsync({
            repository.product(id)
        }) { productItem ->
            val productUI = productItem.map(productItemToProductUiMapper)
            productUiCommunication.updateUi(productUI)
        }
    }
    
    override fun retry(category: String) {
        init(category)
    }

    override fun goToProductsDetails(id: Int) {
        navigation.updateUi(ProductDetailsScreen(id))
    }

    override fun changeAddedToCart(id: Int) {
        runAsync({
            repository.changeAddedToCart(id)
        }) { number ->
            cartBadgeLiveDataWrapper.updateUi(number)
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