package com.example.fakestore.favorites.data

import com.example.fakestore.content.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.favorites.domain.FavoritesRepository
import javax.inject.Inject

class BaseFavoritesRepository @Inject constructor(
    private val favoritesCacheDataSource: ProductFavoritesCacheDataSource,
    private val productCacheDataSource: ProductsCacheDataSource
) : FavoritesRepository {
    override suspend fun init(): List<ProductItem> {
        return favoritesCacheDataSource.productFavorites().map {
            ProductItem.Base(
                it.id,
                it.title,
                it.price,
                it.description,
                it.category,
                it.imageUrl,
                it.rate,
                it.count,
                it.favorite,
                it.addedToCart
            )
        }
    }

    override suspend fun product(id: Int): ProductItem {
        return productCacheDataSource.product(id = id).run {
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

    override suspend fun changeAddedToCart(id: Int): Int {
        return productCacheDataSource.changeItemAddedToCart(id = id)
    }

    override suspend fun changeFavorite(id: Int) {
        productCacheDataSource.changeItemFavorite(id = id)
    }

}
