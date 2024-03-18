package com.example.fakestore.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.content.categories.presentation.CategoryScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigation: Navigation.Mutable,
    private val cartBadgeLiveDataWrapper: CartBadgeLiveDataWrapper,
    private val cartBadgeStorage: CartBadgeStorage.Read
) : ViewModel(), Navigation.Read {

    override fun liveData(): LiveData<Screen> = navigation.liveData()

    fun cartBadgeLiveData(): LiveData<Int> = cartBadgeLiveDataWrapper.liveData()

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            cartBadgeLiveDataWrapper.updateUi(cartBadgeStorage.read())
            navigation.updateUi(CategoryScreen)
        }
    }

    fun navigateToProducts() = navigation.updateUi(CategoryScreen)
}