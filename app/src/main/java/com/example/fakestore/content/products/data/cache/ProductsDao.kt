package com.example.fakestore.content.products.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products WHERE category = :category")
    suspend fun products(category: String): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun product(id: Int): ProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE favorite = 1")
    suspend fun favoritesProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE added_to_cart = 1")
    suspend fun addedToCartProducts(): List<ProductEntity>
}