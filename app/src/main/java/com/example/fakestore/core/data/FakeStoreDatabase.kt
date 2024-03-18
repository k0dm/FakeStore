package com.example.fakestore.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fakestore.content.categories.data.cache.CategoriesDao
import com.example.fakestore.content.categories.data.cache.CategoryEntity
import com.example.fakestore.content.products.data.cache.ProductEntity
import com.example.fakestore.content.products.data.cache.ProductsDao

@Database(
    version = 1,
    entities = [CategoryEntity::class, ProductEntity::class],
    exportSchema = false
)
abstract class FakeStoreDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao

    abstract fun productsDao(): ProductsDao
}