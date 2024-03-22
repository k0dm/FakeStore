package com.example.fakestore.content.categories.domain

import com.example.fakestore.core.domain.LoadResult

interface CategoriesRepository {

    suspend fun categories(): LoadResult<String>
}