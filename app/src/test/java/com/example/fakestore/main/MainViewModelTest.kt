package com.example.fakestore.main

import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.products.categories.presentation.CategoryScreen
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var navigation: FakeNavigation

    @Before
    fun setup() {
        navigation = FakeNavigation.Base()
        viewModel = MainViewModel(navigation = navigation)
    }

    @Test
    fun testFirstRun() {
        viewModel.init(isFirstRun = true)
        navigation.checkScreen(CategoryScreen)
    }

    @Test
    fun testNotFirstRun() {
        viewModel.init(isFirstRun = false)
        navigation.checkScreen(Screen.Empty)
    }

    @Test
    fun testGoToProductsNavigationBar() {
        viewModel.navigateToProducts()
        navigation.checkScreen(CategoryScreen)
    }
}

