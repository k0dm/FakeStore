package com.example.fakestore.content.products.presentation

import androidx.fragment.app.Fragment
import com.example.fakestore.main.Screen

data class ProductsScreen(private val category: String) : Screen.Add(ProductsFragment::class.java) {

    override fun fragment(): Fragment = ProductsFragment.newInstance(category)
}