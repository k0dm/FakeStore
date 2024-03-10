package com.example.fakestore.products.products.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("price")
    val price: Double,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("image_url")
    val imageUrl: String,
    @ColumnInfo("rate")
    val rate: Double,
    @ColumnInfo("count")
    val count: Int,
    @ColumnInfo("favorite")
    val favorite: Boolean,
    @ColumnInfo("added_to_cart")
    val addedToCart: Boolean
)