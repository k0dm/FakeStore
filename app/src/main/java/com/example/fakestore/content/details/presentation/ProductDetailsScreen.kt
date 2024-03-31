package com.example.fakestore.content.details.presentation

import androidx.fragment.app.Fragment
import com.example.fakestore.main.Screen

data class ProductDetailsScreen(
    private val productId: Int,
) : Screen.Add(ProductDetailsFragment::class.java) {

    override fun fragment(): Fragment {
        return ProductDetailsFragment.newInstance(productId)
    }
}
