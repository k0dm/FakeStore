package com.example.fakestore.content.details.presentation

import com.example.fakestore.content.products.domain.ProductItem
import javax.inject.Inject

class ToProductsDetailsUiModelMapper @Inject constructor() :
    ProductItem.Mapper<ProductsDetailsUiModel> {

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
    ): ProductsDetailsUiModel = ProductsDetailsUiModel(
        id, title, price, description, category, imageUrl, rate, count, favorite, addedToCart
    )
}