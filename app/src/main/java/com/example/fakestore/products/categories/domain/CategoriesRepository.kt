package com.example.fakestore.products.categories.domain

interface CategoriesRepository {

    suspend fun loadCategories(): LoadCategoriesResult
}