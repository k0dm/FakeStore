package com.example.fakestore.content.details.presentation

import com.example.fakestore.content.details.domain.ProductsDetailsRepository
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.presentation.RunAsync
import com.example.fakestore.main.CartBadgeLiveDataWrapper
import com.example.fakestore.main.Navigation
import com.example.fakestore.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val navigation: Navigation.Navigate,
    private val communication: ProductDetailsCommunication,
    private val cartBadgeLiveDataWrapper: CartBadgeLiveDataWrapper,
    private val repository: ProductsDetailsRepository,
    private val mapper: ProductItem.Mapper<ProductsDetailsUiModel>,
    runAsync: RunAsync
) : BaseViewModel(runAsync) {

    fun liveData() = communication.liveData()

    fun init(id: Int) {
        runAsync({
            repository.product(id)
        }, {
            communication.updateUi((it.map(mapper)))
        })
    }

    fun goToProducts() {
        navigation.updateUi(Screen.Pop)
    }

    fun changeAddedToCart(id: Int) {
        runAsync({
            repository.changeAddedToCart(id)
        }) { number ->
            cartBadgeLiveDataWrapper.updateUi(number)
        }
    }

    fun changeFavorite(id: Int) = runAsync({
        repository.changeFavorite(id)
    }, {})
}