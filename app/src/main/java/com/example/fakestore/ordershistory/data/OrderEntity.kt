package com.example.fakestore.ordershistory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("orders_history")
data class OrderEntity(
    @PrimaryKey()
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("date")
    val date: String,
    @ColumnInfo("price")
    val price: Double
)
