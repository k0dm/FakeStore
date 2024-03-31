package com.example.fakestore.content.products.data

import com.example.fakestore.content.products.data.cache.ProductEntity
import com.example.fakestore.content.products.data.cloud.ProductResponseMapper

object ToProductEntityMapper : ProductResponseMapper<ProductEntity> {

    override fun map(
        id: Int,
        title: String,
        price: Double,
        description: String,
        category: String,
        imageUrl: String,
        rate: Double,
        count: Int
    ) = ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl,
        rate = rate,
        count = count,
        favorite = false,
        addedToCart = false
    )
}