package com.example.fakestore.main

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var navigation: FakeNavigation

    @Before
    fun setup() {
        navigation = FakeNavigation()
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
        viewModel.navigateToProduct()
        navigation.checkScreen(CategoryScreen)
    }
}

private class FakeNavigation : Navigation.Mutable {

    private var actualScreen: Screen = Screen.Empty

    override fun updateUi(value: Screen) {
        actualScreen = value
    }

    override fun liveData(): LiveData<Screen> {
        throw IllegalAccessException("don't use in unit test")
    }

    fun checkScreen(expectedScreen: Screen) {
        assertEquals(expectedScreen, actualScreen)
    }
}