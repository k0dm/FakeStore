package com.example.fakestore.content.products.domain

import com.example.fakestore.core.domain.LoadResult

interface ProductsRepository {

    suspend fun products(category: String): LoadResult<ProductItem>

    suspend fun product(id: Int): ProductItem

    suspend fun changeAddedToCart(id: Int): Int

    suspend fun changeFavorite(id: Int)
}