package com.example.fakestore.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fakestore.cart.data.cache.CartDao
import com.example.fakestore.cart.data.cache.CartEntity
import com.example.fakestore.content.categories.data.cache.CategoriesDao
import com.example.fakestore.content.categories.data.cache.CategoryEntity
import com.example.fakestore.content.products.data.cache.ProductEntity
import com.example.fakestore.content.products.data.cache.ProductsDao
import com.example.fakestore.ordershistory.data.OrderEntity
import com.example.fakestore.ordershistory.data.OrderHistoryDao
import com.example.fakestore.ordershistory.data.ProductOrderEntity

@Database(
    version = 1,
    entities = [CategoryEntity::class, ProductEntity::class, CartEntity::class, OrderEntity::class, ProductOrderEntity::class],
    exportSchema = false
)
abstract class FakeStoreDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao

    abstract fun productsDao(): ProductsDao

    abstract fun cartDao(): CartDao

    abstract fun ordersHistoryDao(): OrderHistoryDao
}