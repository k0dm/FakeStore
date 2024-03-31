package com.example.fakestore.favorites.presentation

import com.example.fakestore.content.details.presentation.ProductDetailsScreen
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.content.products.presentation.ProductPositionLiveDataWrapper
import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.presentation.RunAsync
import com.example.fakestore.favorites.domain.FavoritesRepository
import com.example.fakestore.favorites.presentation.adapter.ProductFavoriteActions
import com.example.fakestore.favorites.presentation.adapter.ProductFavoriteUi
import com.example.fakestore.main.CartBadgeLiveDataWrapper
import com.example.fakestore.main.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val navigation: Navigation.Navigate,
    private val communication: FavoriteProductsCommunication,
    private val productPositionLiveDataWrapper: ProductPositionLiveDataWrapper.Provide,
    private val repository: FavoritesRepository,
    private val cartBadgeLiveDataWrapper: CartBadgeLiveDataWrapper.Update,
    private val productItemToProductFavoriteUiMapper: ProductItem.Mapper<ProductFavoriteUi>,
    runAsync: RunAsync
) : BaseViewModel(runAsync), ProductFavoriteActions {

    fun liveData() = communication.liveData()

    fun productPositionLiveData() = productPositionLiveDataWrapper.liveData()

    override fun goToProductsDetails(id: Int) {
        navigation.updateUi(ProductDetailsScreen(id))
    }

    fun init() {
        runAsync({
            repository.init()
        }, { listOfProductItems ->
            communication.updateUi(
                if (listOfProductItems.isEmpty()) {
                    ProductFavoriteUiState.NoFavorites
                } else {
                    ProductFavoriteUiState.Success(products = listOfProductItems.map {
                        it.map<ProductFavoriteUi>(
                            productItemToProductFavoriteUiMapper
                        )
                    })
                }
            )
        })
    }

    override fun changeAddedToFavorites(id: Int) {
        runAsync({
            repository.changeFavorite(id = id)
        }) {}
    }

    override fun changeAddedToCart(id: Int) {
        runAsync({ repository.changeAddedToCart(id) })
        {
            cartBadgeLiveDataWrapper.updateUi(it)
        }
    }

}