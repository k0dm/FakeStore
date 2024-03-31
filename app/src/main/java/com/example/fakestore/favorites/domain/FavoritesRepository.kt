package com.example.fakestore.favorites.domain

import com.example.fakestore.content.products.domain.ProductItem

interface FavoritesRepository {

    suspend fun init(): List<ProductItem>

    suspend fun product(id: Int): ProductItem

    suspend fun changeAddedToCart(id: Int): Int

    suspend fun changeFavorite(id: Int)
}