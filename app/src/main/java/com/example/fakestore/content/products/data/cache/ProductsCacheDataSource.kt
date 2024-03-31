package com.example.fakestore.content.products.data.cache

import com.example.fakestore.cart.data.cache.CartCacheDataSource
import com.example.fakestore.main.CartBadgeStorage
import javax.inject.Inject

interface ProductsCacheDataSource {

    suspend fun products(category: String): List<ProductEntity>

    suspend fun cartProducts(): List<ProductEntity>


    suspend fun product(id: Int): ProductEntity

    suspend fun saveProducts(products: List<ProductEntity>)

    suspend fun changeItemFavorite(id: Int)

    suspend fun changeItemAddedToCart(id: Int): Int

    class Base @Inject constructor(
        private val cartCacheDataSource: CartCacheDataSource,
        private val dao: ProductsDao,
        private val cartBadgeStorage: CartBadgeStorage.Save
    ) : ProductsCacheDataSource {

        override suspend fun products(category: String): List<ProductEntity> =
            dao.products(category)

        override suspend fun cartProducts(): List<ProductEntity> = dao.addedToCartProducts()


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
            val isAddedToCart = !productEntity.addedToCart
            val newProduct = productEntity.copy(addedToCart = isAddedToCart)
            if (!isAddedToCart) {
                cartCacheDataSource.removeProductFromCart(id)
            }
            dao.saveProducts(listOf(newProduct))
            val number = dao.addedToCartProducts().size
            cartBadgeStorage.save(number)
            return number
        }
    }
}

