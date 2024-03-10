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
        cacheDataSource.noCache()

        val actualLoadResult = repository.products(category = "category 1")
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
                    ),
                    ProductItem.Base(
                        id = 4,
                        title = "4",
                        price = 1.0,
                        description = "this is 4",
                        category = "category 1",
                        imageUrl = "url/image4",
                        rate = 5.0,
                        count = 5,
                        favorite = false,
                        addedToCart = false
                    ),
                )
            ),
            actualLoadResult
        )

        cacheDataSource.saveProducts(
            listOf(

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
                    category = "category 1",
                    imageUrl = "url/image4",
                    rate = 5.0,
                    count = 5,
                    favorite = false,
                    addedToCart = false
                )
            )
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
                    category = "category 1",
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
        cacheDataSource.noCache()
        cloudDataSource.loadError()

        val actualLoadResult = repository.products(category = "category 1")
        assertEquals(
            LoadResult.Error<String>(message = "Problems"),
            actualLoadResult
        )

        cacheDataSource.saveProducts(listOf())
        cacheDataSource.checkSavedLoadData(listOf())
    }

    @Test
    fun testHaveCache() = runBlocking {
        val actualLoadResult = repository.products(category = "category 1")
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
                    ),
                    ProductItem.Base(
                        id = 2,
                        title = "2",
                        price = 1.0,
                        description = "this is 2",
                        category = "category 1",
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
        repository.changeFavorite(id = 1)
        cacheDataSource.checkAddedToFavorites(id = 1)

        repository.changeFavorite(id = 1)
        cacheDataSource.checkRemovedFromFavorites(id = 1)
    }

    @Test
    fun testChangeCart() = runBlocking {
        repository.changeAddedToCart(id = 1)
        cacheDataSource.checkAddedToCart(id = 1)

        repository.changeAddedToCart(id = 1)
        cacheDataSource.checkRemovedFromCart(id = 1)
    }
}

private class FakeCloudDataSource() : ProductsCloudDataSource {

    private var loadSuccess = true

    override suspend fun loadProducts(category: String): List<ProductResponse> {
        return if (loadSuccess)
            listOf(
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
                ),
                ProductResponse(
                    id = 4,
                    title = "4",
                    price = 1.0,
                    description = "this is 4",
                    category = "category 1",
                    imageUrl = "url/image4",
                    ProductRating(
                        rate = 5.0,
                        count = 5
                    )
                )
            ) else throw IllegalAccessException("Service Unavailable")
    }

    fun loadError() {
        loadSuccess = false
    }
}

private class FakeCacheDataSource() : ProductsCacheDataSource {

    private val cache = arrayListOf<ProductEntity>(
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
        ),
        ProductEntity(
            id = 2,
            title = "2",
            price = 1.0,
            description = "this is 2",
            category = "category 1",
            imageUrl = "url/image2",
            rate = 4.0,
            count = 5,
            favorite = false,
            addedToCart = false,
        )
    )
    private var hasCache = true

    override suspend fun products(): List<ProductEntity> {
        return if (hasCache) cache else emptyList()
    }

    override suspend fun saveProducts(products: List<ProductEntity>) {
        cache.clear()
        cache.addAll(products)
    }

    fun noCache() {
        hasCache = false
    }

    fun checkSavedLoadData(items: List<ProductEntity>) {
        assertEquals(cache, items)
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

    override suspend fun changeItemAddedToCart(id: Int) {
        val item = cache.find { it.id == id }!!
        cache[cache.indexOf(item)] = item.copy(addedToCart = !item.addedToCart)
    }
}