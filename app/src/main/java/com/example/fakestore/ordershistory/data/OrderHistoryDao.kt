package com.example.fakestore.ordershistory.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderHistoryDao {

    @Query("SELECT * FROM orders_history ")
    fun ordersHistory(): List<OrderEntity>

    @Query("SELECT * FROM product_orders_history WHERE order_id = :id")
    fun ordProduct(id: Int): List<ProductOrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrderEntity(orderEntity: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProductOrderEntity(orderEntity: ProductOrderEntity)
}