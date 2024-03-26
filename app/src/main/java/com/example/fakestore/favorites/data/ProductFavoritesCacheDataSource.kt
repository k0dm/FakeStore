package com.example.fakestore.favorites.data

import com.example.fakestore.content.products.data.cache.ProductEntity
import com.example.fakestore.content.products.data.cache.ProductsDao
import javax.inject.Inject

interface ProductFavoritesCacheDataSource {

    suspend fun productFavorites(): List<ProductEntity>

    class Base @Inject constructor(
        private val dao: ProductsDao,
    ) : ProductFavoritesCacheDataSource {

        override suspend fun productFavorites(): List<ProductEntity> = dao.favoritesProducts()
    }
}