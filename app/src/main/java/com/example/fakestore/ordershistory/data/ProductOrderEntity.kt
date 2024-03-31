package com.example.fakestore.ordershistory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_orders_history")
data class ProductOrderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,
    @ColumnInfo("order_id")
    val orderId: String,
    @ColumnInfo("product_id")
    val productId: Int,
    @ColumnInfo("quantity")
    val quantity: Int
)