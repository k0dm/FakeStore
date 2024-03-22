package com.example.fakestore.content.details.data

import com.example.fakestore.content.details.domain.ProductsDetailsRepository
import com.example.fakestore.content.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.content.products.domain.ProductItem
import javax.inject.Inject

class BaseProductsDetailsRepository @Inject constructor(
    private val cacheDataSource: ProductsCacheDataSource
) : ProductsDetailsRepository {

    override suspend fun product(id: Int): ProductItem {
        return cacheDataSource.product(id).run {
            ProductItem.Base(
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
    }

    override suspend fun changeFavorite(id: Int) {
        cacheDataSource.changeItemFavorite(id)
    }

    override suspend fun changeAddedToCart(id: Int): Int {
        return cacheDataSource.changeItemAddedToCart(id)
    }


}