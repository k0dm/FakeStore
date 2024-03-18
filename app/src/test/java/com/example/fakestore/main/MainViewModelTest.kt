package com.example.fakestore.main

import androidx.lifecycle.LiveData
import com.example.fakestore.content.categories.presentation.CategoryScreen
import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.core.presentation.LiveDataWrapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {


    private lateinit var viewModel: MainViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var cartBadgeLiveDataWrapper: FakeCartBadgeLiveDataWrapper
    private lateinit var cartBadgeStorage: FakeCartBadgeStorage

    @Before
    fun setup() {
        navigation = FakeNavigation.Base()
        cartBadgeLiveDataWrapper = FakeCartBadgeLiveDataWrapper()
        cartBadgeStorage = FakeCartBadgeStorage()
        viewModel = MainViewModel(
            navigation = navigation,
            cartBadgeLiveDataWrapper = cartBadgeLiveDataWrapper,
            cartBadgeStorage = cartBadgeStorage
        )
    }

    @Test
    fun testFirstRun() {
        viewModel.init(isFirstRun = true)
        navigation.checkScreen(CategoryScreen)
        cartBadgeLiveDataWrapper.checkUpdatedValue(value = 1)
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


internal class FakeCartBadgeLiveDataWrapper() : CartBadgeLiveDataWrapper, LiveDataWrapper<Int> {

    private var number: Int = -1

    override fun updateUi(value: Int) {
        number = value
    }

    fun checkUpdatedValue(value: Int) = assertEquals(value, number)

    override fun liveData(): LiveData<Int> =
        throw IllegalAccessException("don`t use in unit test idiot")

}


private class FakeCartBadgeStorage : CartBadgeStorage.Read {

    override fun read(): Int = 1

}