package com.example.fakestore.cart.data

import com.example.fakestore.cart.data.cache.CartCacheDataSource
import com.example.fakestore.cart.data.cache.CartEntity
import com.example.fakestore.cart.domain.CartRepository
import com.example.fakestore.cart.domain.ProductCart
import com.example.fakestore.content.products.data.FakeProductsCacheDataSource
import com.example.fakestore.ordershistory.data.OrderEntity
import com.example.fakestore.ordershistory.data.OrdersHistoryCacheDataSource
import com.example.fakestore.ordershistory.data.ProductOrderEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseCartRepositoryTest {

    private lateinit var repository: CartRepository
    private lateinit var cartCacheDataSource: FakeCartCacheDataSource
    private lateinit var productCacheDataSource: FakeProductsCacheDataSource
    private lateinit var ordersHistoryCacheDataSource: FakeOrdersHistoryCacheDataSource
    private lateinit var localDate: FakeLocalDateBase
    private lateinit var generateId: FakeGenerateId

    @Before
    fun setUp() {
        cartCacheDataSource = FakeCartCacheDataSource()
        productCacheDataSource = FakeProductsCacheDataSource()
        ordersHistoryCacheDataSource = FakeOrdersHistoryCacheDataSource()
        localDate = FakeLocalDateBase()
        generateId = FakeGenerateId()
        repository = BaseCartRepository(
            cartCacheDataSource = cartCacheDataSource,
            productCacheDataSource = productCacheDataSource,
            ordersHistoryCacheDataSource = ordersHistoryCacheDataSource,
            localDate = localDate,
            generateId = generateId
        )
    }

    @Test
    fun testHasProductInACart(): Unit = runBlocking {
        productCacheDataSource.hasProductsInCart()
        cartCacheDataSource.hasProductsInCart()

        val actualProducts = repository.cartProducts()
        assertEquals(
            listOf(
                ProductCart(
                    id = 1,
                    title = "1",
                    price = 1.0,
                    description = "this is 1",
                    imageUrl = "url/image1",
                    quantity = 1
                ),
                ProductCart(
                    id = 2,
                    title = "2",
                    price = 2.0,
                    description = "this is 2",
                    imageUrl = "url/image2",
                    quantity = 1
                )
            ),
            actualProducts
        )

        repository.changeAmountProductFromCart(productId = 1, quantity = 2)
        cartCacheDataSource.checkChangeAmount(productId = 1, amount = 2)

        repository.removeFromCart(productId = 1)
        cartCacheDataSource.checkRemovedFromCart(productId = 1)
        productCacheDataSource.checkRemovedFromCart(id = 1)

    }

    @Test
    fun testNoHasProductInACart(): Unit = runBlocking {
        val actualProducts = repository.cartProducts()
        assertEquals(
            emptyList<ProductCart>(),
            actualProducts
        )
    }

    @Test
    fun testProceedToPayment(): Unit = runBlocking {
        productCacheDataSource.hasProductsInCart()
        cartCacheDataSource.hasProductsInCart()

        repository.proceedToPayment()

        cartCacheDataSource.checkNoHasProductsInCart()
        ordersHistoryCacheDataSource.checkAdded(
            listOf(
                OrderEntity(
                    id = "1",
                    date = "March 28, 2024",
                    price = 3.0
                )
            )
        )
    }
}

private class FakeCartCacheDataSource() : CartCacheDataSource {

    private var cache = mutableListOf<CartEntity>()

    override suspend fun cartProducts(): List<CartEntity> {
        return cache
    }

    fun hasProductsInCart() = cache.addAll(
        listOf(
            CartEntity(productId = 1, quantity = 1),
            CartEntity(productId = 2, quantity = 1)
        )
    )


    override suspend fun removeProductFromCart(productId: Int) {
        cache.remove(cache.find { it.productId == productId })
    }

    fun checkRemovedFromCart(productId: Int) =
        assertEquals(null, cache.find { it.productId == productId })


    override suspend fun save(productId: Int, quantity: Int) {
        cache.find { it.productId == productId }?.let {
            cache.remove(it)
        }

        cache.add(CartEntity(productId = productId, quantity = quantity))
    }

    override suspend fun removeAll() {
        cache.clear()
    }

    fun checkNoHasProductsInCart() {
        assertEquals(emptyList<CartEntity>(), cache)
    }

    fun checkChangeAmount(productId: Int, amount: Int) =
        assertEquals(amount, cache.find { it.productId == productId }!!.quantity)
}

internal class FakeOrdersHistoryCacheDataSource() : OrdersHistoryCacheDataSource {

    private val cacheOrderHistory = mutableListOf<OrderEntity>()
    private val cacheProductOrderEntity = mutableListOf<ProductOrderEntity>()

    override suspend fun loadOrderHistory(): List<OrderEntity> = cacheOrderHistory

    override suspend fun add(orderEntity: OrderEntity) {
        cacheOrderHistory.add(orderEntity)
    }

    override suspend fun add(productOrderEntity: ProductOrderEntity) {
        cacheProductOrderEntity.add(productOrderEntity)
    }

    fun checkAdded(expected: List<OrderEntity>) {
        assertEquals(
            expected,
            cacheOrderHistory
        )
    }

}

class FakeLocalDateBase() : LocalDate {
    override fun now(): String = "March 28, 2024"
}

class FakeGenerateId() : GenerateId {

    private var counter = 1

    override fun random(): String = counter++.toString()

}