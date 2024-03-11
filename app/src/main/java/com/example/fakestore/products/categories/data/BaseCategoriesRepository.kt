package com.example.fakestore.products.categories.data

import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.products.categories.data.cache.CategoriesCacheDataSource
import com.example.fakestore.products.categories.data.cloud.CategoriesCloudDataSource
import com.example.fakestore.products.categories.domain.CategoriesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseCategoriesRepository @Inject constructor(
    private val cloudDataSource: CategoriesCloudDataSource,
    private val cacheDataSource: CategoriesCacheDataSource,
    private val handleError: HandleError
) : CategoriesRepository {

    override suspend fun categories(): LoadResult<String> {
        val cacheCategories = cacheDataSource.categories()

        return try {
            if (cacheCategories.isEmpty()) {
                val categories = cloudDataSource.loadCategories()
                cacheDataSource.saveCategories(categories)
                LoadResult.Success(categories)
            } else {
                LoadResult.Success(cacheCategories)
            }
        } catch (e: Exception) {
            LoadResult.Error(handleError.handle(e))
        }
    }
}