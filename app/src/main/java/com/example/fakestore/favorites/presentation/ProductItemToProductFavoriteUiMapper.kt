package com.example.fakestore.favorites.presentation

import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.favorites.presentation.adapter.ProductFavoriteUi
import javax.inject.Inject

class ProductItemToProductFavoriteUiMapper @Inject constructor() :
    ProductItem.Mapper<ProductFavoriteUi> {

    override fun map(
        id: Int,
        title: String,
        price: Double,
        description: String,
        category: String,
        imageUrl: String,
        rate: Double,
        count: Int,
        favorite: Boolean,
        addedToCart: Boolean
    ): ProductFavoriteUi = ProductFavoriteUi.Base(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl,
        rate = rate,
        count = count,
        favorite = favorite,
        addedToCart = addedToCart
    )
}