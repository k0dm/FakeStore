package com.example.fakestore.cart.data.cache

import javax.inject.Inject

interface CartCacheDataSource {

    suspend fun cartProducts(): List<CartEntity>

    suspend fun removeProductFromCart(productId: Int)

    suspend fun save(productId: Int, quantity: Int)

    suspend fun removeAll()

    class Base @Inject constructor(
        private val cartDao: CartDao,
    ) : CartCacheDataSource {

        override suspend fun cartProducts(): List<CartEntity> = cartDao.products()

        override suspend fun removeProductFromCart(productId: Int) {
            cartDao.remove(productId = productId)
        }

        override suspend fun save(productId: Int, quantity: Int) = cartDao.save(
            CartEntity(productId = productId, quantity = quantity)
        )

        override suspend fun removeAll() {
            cartDao.remove()
        }
    }
}