package com.example.fakestore.products.products.presentation

import androidx.fragment.app.FragmentManager
import com.example.fakestore.main.Screen

data class ProductDetailsScreen(
    private val productId: Int,
    private val category: String
) : Screen {

    override fun show(containerId: Int, supportFragmentManager: FragmentManager) {}
}
