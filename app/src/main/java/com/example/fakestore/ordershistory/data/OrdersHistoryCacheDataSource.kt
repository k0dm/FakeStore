package com.example.fakestore.ordershistory.data

import javax.inject.Inject

interface OrdersHistoryCacheDataSource {

    suspend fun loadOrderHistory(): List<OrderEntity>


    suspend fun add(orderEntity: OrderEntity)

    suspend fun add(productOrderEntity: ProductOrderEntity)
    class Base @Inject constructor(
        private val ordersDao: OrderHistoryDao
    ) : OrdersHistoryCacheDataSource {

        override suspend fun loadOrderHistory(): List<OrderEntity> = ordersDao.ordersHistory()

        override suspend fun add(orderEntity: OrderEntity) {
            ordersDao.addOrderEntity(orderEntity)
        }

        override suspend fun add(productOrderEntity: ProductOrderEntity) {
            ordersDao.addProductOrderEntity(productOrderEntity)
        }
    }
}