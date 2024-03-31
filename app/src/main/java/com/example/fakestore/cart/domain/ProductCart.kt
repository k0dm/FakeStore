package com.example.fakestore.cart.domain


data class ProductCart(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val quantity: Int
)
