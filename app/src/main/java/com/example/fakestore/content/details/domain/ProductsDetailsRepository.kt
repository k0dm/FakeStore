package com.example.fakestore.content.details.domain

import com.example.fakestore.content.products.domain.ProductItem

interface ProductsDetailsRepository {

    suspend fun product(id: Int): ProductItem

    suspend fun changeFavorite(id: Int)

    suspend fun changeAddedToCart(id: Int): Int
}