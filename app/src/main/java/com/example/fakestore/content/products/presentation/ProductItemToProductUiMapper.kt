package com.example.fakestore.content.products.presentation

import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.content.products.presentation.adapter.ProductUi
import javax.inject.Inject

class ProductItemToProductUiMapper @Inject constructor() : ProductItem.Mapper<ProductUi> {

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
    ): ProductUi = ProductUi.Base(
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