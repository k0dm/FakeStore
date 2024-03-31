package com.example.fakestore.ordershistory.data

import com.example.fakestore.content.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.ordershistory.domain.Order
import com.example.fakestore.ordershistory.domain.OrderProduct
import com.example.fakestore.ordershistory.domain.OrdersRepository
import javax.inject.Inject

class BaseOrdersRepository @Inject constructor(
    private val ordersHistoryCacheDataSource: OrdersHistoryCacheDataSource,
    private val productsCacheDataSource: ProductsCacheDataSource
) : OrdersRepository {

    override suspend fun orders(): List<Order> {
        var orderNumber = 1
        return ordersHistoryCacheDataSource.loadOrderHistory().map { orderEntity ->

            val productsOrder =
                ordersHistoryCacheDataSource.loadOrderProducts(orderEntity.id).map { product ->
                    val productCache = productsCacheDataSource.product(product.productId)
                    OrderProduct(
                        name = productCache.title,
                        count = product.quantity,
                        price = productCache.price * product.quantity,
                        imageUrl = productCache.imageUrl
                    )
                }
            Order(
                number = orderNumber++,
                date = orderEntity.date,
                totalPrice = orderEntity.price,
                products = productsOrder
            )

        }
    }
}