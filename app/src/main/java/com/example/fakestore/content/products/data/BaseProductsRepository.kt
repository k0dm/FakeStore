package com.example.fakestore.content.products.data

import com.example.fakestore.content.categories.data.HandleError
import com.example.fakestore.content.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.content.products.data.cloud.ProductsCloudDataSource
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.content.products.domain.ProductsRepository
import com.example.fakestore.core.domain.LoadResult
import javax.inject.Inject

class BaseProductsRepository @Inject constructor(
    private val cloudDataSource: ProductsCloudDataSource,
    private val cacheDataSource: ProductsCacheDataSource,
    private val handleError: HandleError
) : ProductsRepository {

    override suspend fun products(category: String): LoadResult<ProductItem> {
        return try {
            val products = cacheDataSource.products(category).ifEmpty {
                val cloudProducts = cloudDataSource.loadProducts(category)
                val productEntities = cloudProducts.map {
                    it.map(ToProductEntityMapper)
                }
                cacheDataSource.saveProducts(productEntities)
                productEntities
            }
            LoadResult.Success(products.map {
                ProductItem.Base(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    description = it.description,
                    category = it.category,
                    imageUrl = it.imageUrl,
                    rate = it.rate,
                    count = it.count,
                    favorite = it.favorite,
                    addedToCart = it.addedToCart
                )
            })
        } catch (e: Exception) {
            LoadResult.Error(handleError.handle(e))
        }
    }

    override suspend fun changeAddedToCart(id: Int): Int = cacheDataSource.changeItemAddedToCart(id)


    override suspend fun changeFavorite(id: Int) = cacheDataSource.changeItemFavorite(id)


}
