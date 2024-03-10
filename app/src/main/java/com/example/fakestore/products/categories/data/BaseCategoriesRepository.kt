package com.example.fakestore.products.categories.data

import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.products.categories.data.cache.CategoriesCacheDataSource
import com.example.fakestore.products.categories.data.cloud.CategoriesCloudDataSource
import com.example.fakestore.products.categories.domain.CategoriesRepository

class BaseCategoriesRepository(
    private val cloudDataSource: CategoriesCloudDataSource,
    private val cacheDataSource: CategoriesCacheDataSource,
    private val handleError: HandleError
) : CategoriesRepository {

    override suspend fun categories(): LoadResult<String> {
        val cacheCategories = cacheDataSource.categories()
        return try {
            val categories = cacheCategories.ifEmpty {
                val cloudCategories = cloudDataSource.loadCategories()
                cacheDataSource.saveCategories(cloudCategories)
                cloudCategories
            }
            LoadResult.Success(categories)
        } catch (e: Exception) {
            LoadResult.Error(handleError.handle(e))
        }
    }
}