package com.example.fakestore.cart.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Query("SELECT * FROM product_cart")
    suspend fun products(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cartEntity: CartEntity)

    @Query("DELETE FROM product_cart WHERE product_id = :productId")
    fun remove(productId: Int)

    @Query("SELECT * FROM product_cart WHERE product_id = :productId")
    suspend fun product(productId: Int): CartEntity

    @Query("DELETE FROM product_cart")
    fun remove()
}