package com.example.fakestore.content.categories.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("categories")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo("category")
    val name: String
)

