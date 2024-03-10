package com.example.fakestore.products.categories.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories")
    fun categories(): List<CategoryEntity>
}