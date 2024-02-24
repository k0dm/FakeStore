package com.example.fakestore.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.products.CategoryScreen

class MainViewModel(
    private val navigation: Navigation.Mutable
) : ViewModel(), Navigation.Read {

    override fun liveData(): LiveData<Screen> = navigation.liveData()

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            navigation.updateUi(CategoryScreen)
        }
    }

    fun navigateToProducts() {
        navigation.updateUi(CategoryScreen)
    }
}