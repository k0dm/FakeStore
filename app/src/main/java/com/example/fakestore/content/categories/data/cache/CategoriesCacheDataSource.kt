package com.example.fakestore.content.categories.data.cache

interface CategoriesCacheDataSource {

    suspend fun categories(): List<String>

    suspend fun saveCategories(categories: List<String>)

    class Base(private val dao: CategoriesDao) : CategoriesCacheDataSource {

        override suspend fun categories() = dao.categories().map { it.name }

        override suspend fun saveCategories(categories: List<String>) =
            dao.save(categories.map { CategoryEntity(it) })
    }
}