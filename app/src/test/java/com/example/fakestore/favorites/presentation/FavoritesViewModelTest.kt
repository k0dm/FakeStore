package com.example.fakestore.favorites.presentation

import androidx.lifecycle.LiveData
import com.example.fakestore.content.details.presentation.FakeProductPositionLiveDataWrapper
import com.example.fakestore.content.details.presentation.ProductDetailsScreen
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.core.FakeRunAsync
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.favorites.domain.FavoritesRepository
import com.example.fakestore.favorites.presentation.adapter.ProductFavoriteUi
import com.example.fakestore.main.FakeCartBadgeLiveDataWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class FavoritesViewModelTest {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var repository: FakeFavoritesRepository
    private lateinit var cartBadgeLiveDataWrapper: FakeCartBadgeLiveDataWrapper
    private lateinit var productItemToProductUiMapper: FakeProductItemToProductFavoriteUiMapper
    private lateinit var productPositionLiveDataWrapper: FakeProductPositionLiveDataWrapper
    private lateinit var productFavoriteCommunication: FakeFavoritesProductsCommunication
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setUp() {
        navigation = FakeNavigation.Base()
        repository = FakeFavoritesRepository()
        cartBadgeLiveDataWrapper = FakeCartBadgeLiveDataWrapper()
        productPositionLiveDataWrapper = FakeProductPositionLiveDataWrapper()
        productItemToProductUiMapper = FakeProductItemToProductFavoriteUiMapper()
        productFavoriteCommunication = FakeFavoritesProductsCommunication()
        runAsync = FakeRunAsync()
        favoritesViewModel = FavoritesViewModel(
            navigation = navigation,
            repository = repository,
            cartBadgeLiveDataWrapper = cartBadgeLiveDataWrapper,
            productPositionLiveDataWrapper = productPositionLiveDataWrapper,
            productItemToProductFavoriteUiMapper = productItemToProductUiMapper,
            communication = productFavoriteCommunication,
            runAsync = runAsync,
        )
    }

    @Test
    fun testInitHasCache() = runBlocking {
        repository.hasCache()

        favoritesViewModel.init()
        runAsync.pingResult()

        productFavoriteCommunication.checkUiState(
            ProductFavoriteUiState.Success(
                products = listOf(
                    ProductFavoriteUi.Base(
                        id = 2,
                        title = "2",
                        price = 1.0,
                        description = "this is 2",
                        category = "category 1",
                        imageUrl = "url/image2",
                        rate = 5.0,
                        count = 5,
                        favorite = true,
                        addedToCart = false
                    ),
                    ProductFavoriteUi.Base(
                        id = 3,
                        title = "3",
                        price = 1.0,
                        description = "this is 3",
                        category = "category 1",
                        imageUrl = "url/image3",
                        rate = 5.0,
                        count = 5,
                        favorite = true,
                        addedToCart = false
                    )
                )
            )
        )
    }

    @Test
    fun testInitNoCache() = runBlocking {
        favoritesViewModel.init()
        runAsync.pingResult()

        productFavoriteCommunication.checkUiState(ProductFavoriteUiState.NoFavorites)
    }

    @Test
    fun testChangeAddedToCart() = runBlocking {
        repository.hasCache()

        favoritesViewModel.changeAddedToCart(id = 2)
        runAsync.pingResult()
        assertEquals(
            LoadResult.Success(
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
                        favorite = true,
                        addedToCart = true

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
                        favorite = true,
                        addedToCart = false
                    )
                )
            ),
            LoadResult.Success(repository.init())
        )
        cartBadgeLiveDataWrapper.checkUpdatedValue(1)
    }

    @Test
    fun testChangeAddedToFavorite() = runBlocking {
        repository.hasCache()
        favoritesViewModel.changeAddedToFavorites(id = 2)
        assertEquals(
            LoadResult.Success(
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
                        favorite = true,
                        addedToCart = false
                    )
                )
            ),
            LoadResult.Success(
                repository.init()
            ),
        )
    }

    @Test
    fun testGoToProductDetails() {
        favoritesViewModel.goToProductsDetails(id = 1)
        navigation.checkScreen(ProductDetailsScreen(productId = 1))
    }
}

private class FakeFavoritesRepository() : FavoritesRepository {

    private var cache = mutableListOf<ProductItem>()

    fun hasCache() {
        cache.addAll(
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
                    favorite = true,
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
                    favorite = true,
                    addedToCart = false
                )
            )
        )
    }

    override suspend fun init(): List<ProductItem> = cache

    override suspend fun product(id: Int): ProductItem = if (id == 1) {
        cache[0]
    } else {
        cache[1]
    }

    var sizeCart = 0

    override suspend fun changeAddedToCart(id: Int): Int {
        if (id == 2) {
            cache[0] = ProductItem.Base(
                id = 2,
                title = "2",
                price = 1.0,
                description = "this is 2",
                category = "category 1",
                imageUrl = "url/image2",
                rate = 5.0,
                count = 5,
                favorite = true,
                addedToCart = true
            )
        } else {
            cache[1] = ProductItem.Base(
                id = 3,
                title = "3",
                price = 1.0,
                description = "this is 3",
                category = "category 1",
                imageUrl = "url/image3",
                rate = 5.0,
                count = 5,
                favorite = true,
                addedToCart = false
            )
        }
        return ++sizeCart
    }

    override suspend fun changeFavorite(id: Int) {
        if (id == 2) {
            cache[0] = ProductItem.Base(
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
            )
        } else {
            cache[1] = ProductItem.Base(
                id = 3,
                title = "3",
                price = 1.0,
                description = "this is 3",
                category = "category 1",
                imageUrl = "url/image3",
                rate = 5.0,
                count = 5,
                favorite = true,
                addedToCart = false
            )
        }
    }
}

private class FakeFavoritesProductsCommunication : FavoriteProductsCommunication {

    private var actualUiState: ProductFavoriteUiState = ProductFavoriteUiState.NoFavorites

    override fun updateUi(value: ProductFavoriteUiState) {
        actualUiState = value
    }

    override fun liveData(): LiveData<ProductFavoriteUiState> {
        throw IllegalAccessException("Don`t use in test")
    }

    fun checkUiState(uiState: ProductFavoriteUiState) {
        assertEquals(uiState, actualUiState)
    }
}

private class FakeProductItemToProductFavoriteUiMapper() : ProductItem.Mapper<ProductFavoriteUi> {
    override fun map(
        id: Int,
        title: String,
        price: Double,
        description: String,
        category: String,
        imageUrl: String,
        rate: Double,
        count: Int,
        favorite: Boolean,
        addedToCart: Boolean
    ): ProductFavoriteUi = ProductFavoriteUi.Base(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl,
        rate = rate,
        count = count,
        favorite = favorite,
        addedToCart = addedToCart
    )
}