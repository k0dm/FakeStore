package com.example.fakestore.cart.data

import com.example.fakestore.cart.data.cache.CartCacheDataSource
import com.example.fakestore.cart.domain.CartRepository
import com.example.fakestore.cart.domain.ProductCart
import com.example.fakestore.content.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.ordershistory.data.OrderEntity
import com.example.fakestore.ordershistory.data.OrdersHistoryCacheDataSource
import com.example.fakestore.ordershistory.data.ProductOrderEntity
import javax.inject.Inject

class BaseCartRepository @Inject constructor(
    private val cartCacheDataSource: CartCacheDataSource,
    private val productCacheDataSource: ProductsCacheDataSource,
    private val ordersHistoryCacheDataSource: OrdersHistoryCacheDataSource,
    private val generateId: GenerateId,
    private val localDate: LocalDate
) : CartRepository {

    override suspend fun cartProducts(): List<ProductCart> {
        val productsQuantity = cartCacheDataSource.cartProducts()
        return productCacheDataSource.cartProducts().map { productEntity ->
            val productCart = productsQuantity.find { it.productId == productEntity.id }
            var quantity = 1
            if (productCart == null) {
                cartCacheDataSource.save(productEntity.id, quantity)
            } else {
                quantity = productCart.quantity
            }

            ProductCart(
                id = productEntity.id,
                title = productEntity.title,
                price = productEntity.price,
                description = productEntity.description,
                imageUrl = productEntity.imageUrl,
                quantity = quantity
            )
        }
    }

    override suspend fun changeAmountProductFromCart(productId: Int, quantity: Int) {
        cartCacheDataSource.save(productId, quantity)
    }

    override suspend fun removeFromCart(productId: Int): Int {
        cartCacheDataSource.removeProductFromCart(productId)
        return productCacheDataSource.changeItemAddedToCart(productId)
    }

    override suspend fun proceedToPayment() {
        var price = 0.0
        val orderId = generateId.random()
        val productCarts = cartCacheDataSource.cartProducts()
        productCarts.forEach() {
            ordersHistoryCacheDataSource.add(
                ProductOrderEntity(
                    orderId = orderId,
                    productId = it.productId,
                    quantity = it.quantity
                )
            )
            productCacheDataSource.changeItemAddedToCart(it.productId)
            price += productCacheDataSource.product(it.productId).price * it.quantity
        }

        ordersHistoryCacheDataSource.add(
            orderEntity = OrderEntity(
                id = orderId,
                date = localDate.now(),
                price = price
            )
        )
        cartCacheDataSource.removeAll()
    }

    override suspend fun subtotal(): Double {
        var price = 0.0
        val productCarts = cartCacheDataSource.cartProducts()
        productCarts.forEach() {
            price += productCacheDataSource.product(it.productId).price * it.quantity
        }
        return price
    }
}

