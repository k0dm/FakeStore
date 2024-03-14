package com.example.fakestore.products.products.presentation.adapter

interface ProductUi {

    data class Base(
        private val id: Int,
        private val title: String,
        private val price: Double,
        private val description: String,
        private val category: String,
        private val imageUrl: String,
        private val rate: Double,
        private val count: Int,
        private val favorite: Boolean,
        private val addedToCart: Boolean
    ) : ProductUi
}