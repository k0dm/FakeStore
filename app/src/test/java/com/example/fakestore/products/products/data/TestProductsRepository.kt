package com.example.fakestore.products.products.data

import com.example.fakestore.core.FakeHandleError
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.products.products.data.cache.ProductEntity
import com.example.fakestore.products.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.products.products.data.cloud.ProductRating
import com.example.fakestore.products.products.data.cloud.ProductResponse
import com.example.fakestore.products.products.data.cloud.ProductsCloudDataSource
import com.example.fakestore.products.products.domain.ProductItem
import com.example.fakestore.products.products.domain.ProductsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductsRepositoryTest {

    private lateinit var repository: ProductsRepository
    private lateinit var cloudDataSource: FakeCloudDataSource
    private lateinit var cacheDataSource: FakeCacheDataSource
    private lateinit var handleError: FakeHandleError

    @Before
    fun setUp() {
        cloudDataSource = FakeCloudDataSource()
        cacheDataSource = FakeCacheDataSource()
        handleError = FakeHandleError()
        repository = BaseProductsRepository(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            handleError = handleError,
        )
    }

    @Test
    fun testNoCacheAndLoadSuccess() = runBlocking() {
        var actualLoadResult = repository.products(category = "category 1")
        assertEquals(
            LoadResult.Success(
                items = listOf(
                    ProductItem.Base(
                        id = 3,
                        title = "3",
                        price = 1.0,
                        description = "this is 3",
                        category = "category 1",
                        imageUrl = "url/image3",
                        rate = 5.0,
                        count = 5,
                        favorite = false,
                        addedToCart = false
                    )
                )
            ),
            actualLoadResult
        )


        cacheDataSource.checkSavedLoadData(
            items = listOf(
                ProductEntity(
                    id = 3,
                    title = "3",
                    price = 1.0,
                    description = "this is 3",
                    category = "category 1",
                    imageUrl = "url/image3",
                    rate = 5.0,
                    count = 5,
                    favorite = false,
                    addedToCart = false
                )
            )
        )

        actualLoadResult = repository.products(category = "category 2")
        assertEquals(
            LoadResult.Success(
                items = listOf(
                    ProductItem.Base(
                        id = 4,
                        title = "4",
                        price = 1.0,
                        description = "this is 4",
                        category = "category 2",
                        imageUrl = "url/image4",
                        rate = 5.0,
                        count = 5,
                        favorite = false,
                        addedToCart = false
                    )
                )
            ),
            actualLoadResult
        )

        cacheDataSource.checkSavedLoadData(
            items = listOf(

                ProductEntity(
                    id = 3,
                    title = "3",
                    price = 1.0,
                    description = "this is 3",
                    category = "category 1",
                    imageUrl = "url/image3",
                    rate = 5.0,
                    count = 5,
                    favorite = false,
                    addedToCart = false
                ),
                ProductEntity(
                    id = 4,
                    title = "4",
                    price = 1.0,
                    description = "this is 4",
                    category = "category 2",
                    imageUrl = "url/image4",
                    rate = 5.0,
                    count = 5,
                    favorite = false,
                    addedToCart = false
                )
            )
        )
    }

    @Test
    fun testNoCacheAndLoadError() = runBlocking {
        cloudDataSource.loadError()

        val actualLoadResult = repository.products(category = "category 2")
        assertEquals(
            LoadResult.Error<String>("Problems"),
            actualLoadResult
        )

        cacheDataSource.checkSavedLoadData(listOf())
    }

    @Test
    fun testHaveCache() = runBlocking {
        cacheDataSource.hasCache()

        var actualLoadResult = repository.products(category = "category 1")
        assertEquals(
            LoadResult.Success(
                items = listOf(
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
                        addedToCart = false,
                    )
                )
            ),
            actualLoadResult
        )
        actualLoadResult = repository.products(category = "category 2")
        assertEquals(
            LoadResult.Success(
                items = listOf(
                    ProductItem.Base(
                        id = 2,
                        title = "2",
                        price = 1.0,
                        description = "this is 2",
                        category = "category 2",
                        imageUrl = "url/image2",
                        rate = 4.0,
                        count = 5,
                        favorite = false,
                        addedToCart = false,
                    ),
                )
            ),
            actualLoadResult
        )
    }

    @Test
    fun testChangeFavorite() = runBlocking {
        cacheDataSource.hasCache()

        repository.changeFavorite(id = 1)
        cacheDataSource.checkAddedToFavorites(id = 1)

        repository.changeFavorite(id = 1)
        cacheDataSource.checkRemovedFromFavorites(id = 1)
    }

    @Test
    fun testChangeCart() = runBlocking {
        cacheDataSource.hasCache()

        var size = repository.changeAddedToCart(id = 1)
        assertEquals(1, size)
        cacheDataSource.checkAddedToCart(id = 1)

        size = repository.changeAddedToCart(id = 1)
        assertEquals(0, size)
        cacheDataSource.checkRemovedFromCart(id = 1)
    }
}

private class FakeCloudDataSource() : ProductsCloudDataSource {

    private var loadSuccess = true

    override suspend fun loadProducts(category: String): List<ProductResponse> {
        return if (loadSuccess)
            listOf(
                if (category == "category 1") {
                    ProductResponse(
                        id = 3,
                        title = "3",
                        price = 1.0,
                        description = "this is 3",
                        category = "category 1",
                        imageUrl = "url/image3",
                        ProductRating(
                            rate = 5.0,
                            count = 5
                        )
                    )
                } else {
                    ProductResponse(
                        id = 4,
                        title = "4",
                        price = 1.0,
                        description = "this is 4",
                        category = "category 2",
                        imageUrl = "url/image4",
                        ProductRating(
                            rate = 5.0,
                            count = 5
                        )
                    )
                }


            ) else throw IllegalAccessException("Service Unavailable")
    }

    fun loadError() {
        loadSuccess = false
    }
}

private class FakeCacheDataSource() : ProductsCacheDataSource {

    private val cache = arrayListOf<ProductEntity>()

    private var hasCache = false

    override suspend fun products(category: String): List<ProductEntity> {
        return if (hasCache) cache.filter {
            it.category == category
        } else emptyList()
    }

    override suspend fun saveProducts(products: List<ProductEntity>) {
        cache.addAll(products)
    }

    fun hasCache() {
        hasCache = true
        cache.add(
            ProductEntity(
                id = 1,
                title = "1",
                price = 1.0,
                description = "this is 1",
                category = "category 1",
                imageUrl = "url/image1",
                rate = 5.0,
                count = 5,
                favorite = false,
                addedToCart = false,
            )
        )
        cache.add(
            ProductEntity(
                id = 2,
                title = "2",
                price = 1.0,
                description = "this is 2",
                category = "category 2",
                imageUrl = "url/image2",
                rate = 4.0,
                count = 5,
                favorite = false,
                addedToCart = false,
            )
        )
    }

    fun checkSavedLoadData(items: List<ProductEntity>) {
        assertEquals(items, cache)
    }

    fun checkAddedToFavorites(id: Int) {
        assertTrue(cache.find { it.id == id }!!.favorite)
    }

    fun checkRemovedFromFavorites(id: Int) {
        assertFalse(cache.find { it.id == id }!!.favorite)
    }

    fun checkAddedToCart(id: Int) {
        assertTrue(cache.find { it.id == id }!!.addedToCart)
    }

    fun checkRemovedFromCart(id: Int) {
        assertFalse(cache.find { it.id == id }!!.addedToCart)
    }

    override suspend fun changeItemFavorite(id: Int) {
        val item = cache.find { it.id == id }!!
        cache[cache.indexOf(item)] = item.copy(favorite = !item.favorite)
    }

    override suspend fun changeItemAddedToCart(id: Int): Int {
        val item = cache.find { it.id == id }!!

        cache[cache.indexOf(item)] = item.copy(addedToCart = !item.addedToCart)
        return if (item.addedToCart) 0 else 1
    }
}