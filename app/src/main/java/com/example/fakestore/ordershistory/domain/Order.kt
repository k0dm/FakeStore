package com.example.fakestore.ordershistory.domain

data class Order(
    val number: Int,
    val date: String,
    val totalPrice: Double,
    val products: List<OrderProduct>
)