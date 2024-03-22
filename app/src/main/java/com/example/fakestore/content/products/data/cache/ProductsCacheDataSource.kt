package com.example.fakestore.content.products.data.cache

import com.example.fakestore.main.CartBadgeStorage
import javax.inject.Inject

interface ProductsCacheDataSource {

    suspend fun products(category: String): List<ProductEntity>

    suspend fun product(id: Int): ProductEntity

    suspend fun saveProducts(products: List<ProductEntity>)

    suspend fun changeItemFavorite(id: Int)

    suspend fun changeItemAddedToCart(id: Int): Int

    class Base @Inject constructor(
        private val dao: ProductsDao,
        private val cartBadgeStorage: CartBadgeStorage.Save
    ) : ProductsCacheDataSource {

        override suspend fun products(category: String): List<ProductEntity> =
            dao.products(category)

        override suspend fun product(id: Int): ProductEntity = dao.product(id)

        override suspend fun saveProducts(products: List<ProductEntity>) {
            dao.saveProducts(products)
        }

        override suspend fun changeItemFavorite(id: Int) {
            val productEntity: ProductEntity = dao.product(id)
            val newProducts = productEntity.copy(favorite = !productEntity.favorite)
            dao.saveProducts(listOf(newProducts))
        }

        override suspend fun changeItemAddedToCart(id: Int): Int {
            val productEntity: ProductEntity = dao.product(id)
            val newProducts = productEntity.copy(addedToCart = !productEntity.addedToCart)
            dao.saveProducts(listOf(newProducts))
            val number = dao.addedToCartProducts().size
            cartBadgeStorage.save(number)
            return number
        }
    }
}

