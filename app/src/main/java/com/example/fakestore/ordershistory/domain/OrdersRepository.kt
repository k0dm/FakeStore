package com.example.fakestore.ordershistory.domain

interface OrdersRepository {

    suspend fun orders(): List<Order>
}
