package com.example.fakestore.content.categories.data.cloud

import retrofit2.Call
import retrofit2.http.GET

interface CategoriesCloudDataSource {

    suspend fun loadCategories(): List<String>

    class Base(private val categoryService: CategoryService) : CategoriesCloudDataSource {

        override suspend fun loadCategories(): List<String> {
            return categoryService.categories().execute().body()!!
        }
    }
}

interface CategoryService {

    @GET("/products/categories")
    fun categories(): Call<List<String>>
}