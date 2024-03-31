package com.example.fakestore.core

import androidx.lifecycle.LiveData
import com.example.fakestore.main.Navigation
import com.example.fakestore.main.Screen
import org.junit.Assert.assertEquals

internal interface FakeNavigation : Navigation.Mutable, FakeNavigationUpdate {

    class Base : FakeNavigation {
        private var actualScreen: Screen = Screen.Empty

        override fun updateUi(value: Screen) {
            actualScreen = value
        }

        override fun liveData(): LiveData<Screen> {
            throw IllegalAccessException("don't use in unit test")
        }

        override fun checkScreen(expectedScreen: Screen) {
            assertEquals(expectedScreen, actualScreen)
        }
    }
}

internal interface FakeNavigationUpdate : Navigation.Navigate {

    fun checkScreen(expectedScreen: Screen)
}