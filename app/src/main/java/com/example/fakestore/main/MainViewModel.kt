package com.example.fakestore.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.products.categories.presentation.CategoryScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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