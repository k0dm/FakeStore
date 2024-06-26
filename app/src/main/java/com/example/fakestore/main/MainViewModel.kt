package com.example.fakestore.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.cart.presentation.CartScreen
import com.example.fakestore.content.categories.presentation.CategoryScreen
import com.example.fakestore.favorites.presentation.FavoriteScreen
import com.example.fakestore.ordershistory.presentation.OrdersScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigation: Navigation.Mutable,
    private val cartBadgeLiveDataWrapper: CartBadgeLiveDataWrapper.Mutable,
    private val cartBadgeStorage: CartBadgeStorage.Read
) : ViewModel() {

    fun navigationLiveData(): LiveData<Screen> = navigation.liveData()

    fun cartBadgeLiveData(): LiveData<Int> = cartBadgeLiveDataWrapper.liveData()

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            cartBadgeLiveDataWrapper.updateUi(cartBadgeStorage.read())
            navigation.updateUi(CategoryScreen)
        }
    }

    fun navigateToProducts() = navigation.updateUi(CategoryScreen)

    fun navigateToFavorites() = navigation.updateUi(FavoriteScreen)

    fun navigateToCart() = navigation.updateUi(CartScreen)

    fun navigateToOrders() = navigation.updateUi(OrdersScreen)

}