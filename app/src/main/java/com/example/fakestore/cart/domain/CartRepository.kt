package com.example.fakestore.cart.domain

interface CartRepository {

    suspend fun cartProducts(): List<ProductCart>

    suspend fun changeAmountProductFromCart(productId: Int, quantity: Int)

    suspend fun removeFromCart(productId: Int): Int

    suspend fun proceedToPayment()

    suspend fun subtotal(): Double
}