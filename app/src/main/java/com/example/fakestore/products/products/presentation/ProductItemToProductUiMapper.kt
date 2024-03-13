package com.example.fakestore.products.products.presentation

import com.example.fakestore.products.products.domain.ProductItem
import com.example.fakestore.products.products.presentation.adapter.ProductUi

class ProductItemToProductUiMapper : ProductItem.Mapper<ProductUi> {
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
        id,
        title,
        price,
        description,
        category,
        imageUrl,
        rate,
        count,
        favorite,
        addedToCart
    )


}