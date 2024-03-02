package com.example.fakestore.products.categories.data.cache

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity("categories")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo("category")
    val name: String
)

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories")
    fun categories(): List<CategoryEntity>
}
