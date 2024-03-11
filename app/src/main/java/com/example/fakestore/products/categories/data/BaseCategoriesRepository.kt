package com.example.fakestore.products.categories.data

import com.example.fakestore.products.categories.data.cache.CategoriesCacheDataSource
import com.example.fakestore.products.categories.data.cloud.CategoriesCloudDataSource
import com.example.fakestore.products.categories.domain.CategoriesRepository
import com.example.fakestore.products.categories.domain.LoadCategoriesResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseCategoriesRepository @Inject constructor(
    private val cloudDataSource: CategoriesCloudDataSource,
    private val cacheDataSource: CategoriesCacheDataSource,
    private val handleError: HandleError
) : CategoriesRepository {

    override suspend fun loadCategories(): LoadCategoriesResult {
        val cacheCategories = cacheDataSource.categories()

        return try {
            if (cacheCategories.isEmpty()) {
                val categories = cloudDataSource.loadCategories()
                cacheDataSource.saveCategories(categories)
                LoadCategoriesResult.Success(categories)
            } else {
                LoadCategoriesResult.Success(cacheCategories)
            }
        } catch (e: Exception) {
            LoadCategoriesResult.Error(handleError.handle(e))
        }
    }
}