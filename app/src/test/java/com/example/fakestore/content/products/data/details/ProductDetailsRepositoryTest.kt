package com.example.fakestore.content.products.data.details

import com.example.fakestore.content.details.data.BaseProductsDetailsRepository
import com.example.fakestore.content.products.data.cache.ProductEntity
import com.example.fakestore.content.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.content.products.domain.ProductItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductDetailsRepositoryTest {

    private lateinit var repository: BaseProductsDetailsRepository
    private lateinit var cacheDataSource: FakeProductsCacheDataSource

    @Before
    fun setUp() {
        cacheDataSource = FakeProductsCacheDataSource()
        repository = BaseProductsDetailsRepository(cacheDataSource = cacheDataSource)
    }

    @Test
    fun testGetTheProductById() = runBlocking {
        val actualProduct = repository.product(id = 1)
        assertEquals(
            ProductItem.Base(
                id = 1,
                title = "1",
                price = 1.0,
                description = "this is 1",
                category = "category 1",
                imageUrl = "url/image1",
                rate = 5.0,
                count = 5,
                favorite = false,
                addedToCart = false
            ),
            actualProduct
        )
    }

    @Test
    fun testChangeFavorite() = runBlocking {
        repository.changeFavorite(id = 1)
        cacheDataSource.checkAddedToFavorites()

        repository.changeFavorite(id = 1)
        cacheDataSource.checkRemovedFromFavorites()
    }

    @Test
    fun testChangeCart() = runBlocking {
        var size = repository.changeAddedToCart(id = 1)
        assertEquals(1, size)
        cacheDataSource.checkAddedToCart()

        size = repository.changeAddedToCart(id = 1)
        assertEquals(0, size)
        cacheDataSource.checkRemovedFromCart()
    }
}

private class FakeProductsCacheDataSource() : ProductsCacheDataSource {

    private var cache = ProductEntity(
        id = 1,
        title = "1",
        price = 1.0,
        description = "this is 1",
        category = "category 1",
        imageUrl = "url/image1",
        rate = 5.0,
        count = 5,
        favorite = false,
        addedToCart = false
    )

    override suspend fun products(category: String): List<ProductEntity> {
        return listOf(cache)
    }

    override suspend fun product(id: Int): ProductEntity = cache

    override suspend fun saveProducts(products: List<ProductEntity>) {
        cache = products[0]
    }

    override suspend fun changeItemFavorite(id: Int) {
        val oldProduct = cache
        val newProduct = oldProduct.copy(favorite = !oldProduct.favorite)
        cache = newProduct
    }

    fun checkAddedToFavorites() {
        Assert.assertTrue(cache.favorite == true)
    }

    fun checkRemovedFromFavorites() {
        Assert.assertTrue(cache.favorite == false)
    }

    override suspend fun changeItemAddedToCart(id: Int): Int {
        val oldProduct = cache
        val newProduct = oldProduct.copy(addedToCart = !oldProduct.addedToCart)
        cache = newProduct
        return if (cache.addedToCart) 1 else 0
    }

    fun checkAddedToCart() {
        Assert.assertTrue(cache.addedToCart == true)
    }

    fun checkRemovedFromCart() {
        Assert.assertTrue(cache.addedToCart == false)
    }
}