package com.example.fakestore.products.categories.domain

import com.example.fakestore.core.domain.LoadResult

interface CategoriesRepository {

    suspend fun categories(): LoadResult<String>
}