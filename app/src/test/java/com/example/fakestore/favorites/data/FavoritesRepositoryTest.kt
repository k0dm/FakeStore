package com.example.fakestore.favorites.data


import com.example.fakestore.content.products.data.FakeProductsCacheDataSource
import com.example.fakestore.content.products.data.cache.ProductEntity
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.favorites.domain.FavoritesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavoritesRepositoryTest {

    private lateinit var repository: FavoritesRepository
    private lateinit var favoritesCacheDataSource: FakeProductFavoritesCacheDataSource
    private lateinit var productCacheDataSource: FakeProductsCacheDataSource

    @Before
    fun setUp() {
        favoritesCacheDataSource = FakeProductFavoritesCacheDataSource()
        productCacheDataSource = FakeProductsCacheDataSource()
        repository = BaseFavoritesRepository(
            favoritesCacheDataSource = favoritesCacheDataSource,
            productCacheDataSource = productCacheDataSource,
        )
    }

    @Test
    fun testFavoritesCache() = runBlocking {
        var actualItems = repository.init()

        assertEquals(
            listOf(
                ProductItem.Base(
                    id = 2,
                    title = "2",
                    price = 1.0,
                    description = "this is 2",
                    category = "category 1",
                    imageUrl = "url/image2",
                    rate = 5.0,
                    count = 5,
                    favorite = false,
                    addedToCart = false
                ),
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
            ),
            actualItems
        )
    }

    @Test
    fun testChangeFavorite() = runBlocking {// todo
        productCacheDataSource.hasCache()

        repository.changeFavorite(id = 1)
        productCacheDataSource.checkAddedToFavorites(id = 1)

        repository.changeFavorite(id = 1)
        productCacheDataSource.checkRemovedFromFavorites(id = 1)
    }

    @Test
    fun testChangeCart() = runBlocking {
        productCacheDataSource.hasCache()

        var size = repository.changeAddedToCart(id = 1)
        assertEquals(1, size)
        productCacheDataSource.checkAddedToCart(id = 1)

        size = repository.changeAddedToCart(id = 1)
        assertEquals(0, size)
        productCacheDataSource.checkRemovedFromCart(id = 1)
    }
}

private class FakeProductFavoritesCacheDataSource() : ProductFavoritesCacheDataSource {

    override suspend fun productFavorites(): List<ProductEntity> {
        return listOf(
            ProductEntity(
                id = 2,
                title = "2",
                price = 1.0,
                description = "this is 2",
                category = "category 1",
                imageUrl = "url/image2",
                rate = 5.0,
                count = 5,
                favorite = false,
                addedToCart = false
            ),
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
    }

}