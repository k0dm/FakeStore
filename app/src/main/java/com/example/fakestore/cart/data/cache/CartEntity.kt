package com.example.fakestore.cart.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("product_cart")
data class CartEntity(
    @PrimaryKey
    @ColumnInfo("product_id")
    val productId: Int,
    @ColumnInfo("quantity")
    var quantity: Int = 1
)