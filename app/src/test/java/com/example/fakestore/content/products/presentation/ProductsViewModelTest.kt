package com.example.fakestore.content.products.presentation

import androidx.lifecycle.LiveData
import com.example.fakestore.content.details.presentation.FakeProductPositionLiveDataWrapper
import com.example.fakestore.content.details.presentation.ProductDetailsScreen
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.content.products.domain.ProductsRepository
import com.example.fakestore.content.products.presentation.adapter.ProductUi
import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.core.FakeRunAsync
import com.example.fakestore.core.UiUpdate
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.main.CartBadgeLiveDataWrapper
import com.example.fakestore.main.Screen
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductsViewModelTest {

    private lateinit var viewModel: ProductsViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var communication: FakeProductsCommunication
    private lateinit var productUiCommunication: FakeProductUiCommunication
    private lateinit var cartBadgeLiveDataWrapper: FakeCartBadgeLiveDataWrapper
    private lateinit var productPositionLiveDataWrapper: FakeProductPositionLiveDataWrapper
    private lateinit var repository: FakeProductsRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var mapper: FakeMapper
    private lateinit var productItemToProductUiMapper: FakeProductItemToProductUiMapper

    @Before
    fun setUp() {
        navigation = FakeNavigation.Base()
        communication = FakeProductsCommunication()
        productUiCommunication = FakeProductUiCommunication()
        cartBadgeLiveDataWrapper = FakeCartBadgeLiveDataWrapper()
        productPositionLiveDataWrapper = FakeProductPositionLiveDataWrapper()
        repository = FakeProductsRepository()
        runAsync = FakeRunAsync()
        mapper = FakeMapper()
        productItemToProductUiMapper = FakeProductItemToProductUiMapper()
        viewModel = ProductsViewModel(
            navigation = navigation,
            communication = communication,
            productUiCommunication = productUiCommunication,
            cartBadgeLiveDataWrapper = cartBadgeLiveDataWrapper,
            productPositionLiveDataWrapper = productPositionLiveDataWrapper,
            repository = repository,
            mapper = mapper,
            productItemToProductUiMapper = productItemToProductUiMapper,
            runAsync = runAsync,
        )
    }

    @Test
    fun testInitLoadSuccess() {
        repository.loadSuccess()

        viewModel.init(category = "category 1")
        communication.checkUiState(ProductsUiState.Progress)

        runAsync.pingResult()
        mapper.checkMappedSuccess(
            expectedItems = listOf(
                ProductItem.Base(
                    id = 1,
                    title = "product 1",
                    price = 1.0,
                    description = "this 1",
                    category = "category 1",
                    imageUrl = "url/image1",
                    rate = 5.0,
                    count = 4,
                    favorite = false,
                    addedToCart = false
                ),
                ProductItem.Base(
                    id = 2,
                    title = "product 2",
                    price = 2.0,
                    description = "this 2",
                    category = "category 1",
                    imageUrl = "url/image2",
                    rate = 5.0,
                    count = 4,
                    favorite = false,
                    addedToCart = false
                ),
            )
        )
    }

    @Test
    fun testInitLoadError() {
        repository.loadError()

        viewModel.init(category = "category 1")
        communication.checkUiState(ProductsUiState.Progress)

        runAsync.pingResult()
        mapper.checkMappedError(expectedMessage = "Problems")
    }

    @Test
    fun testInitErrorThenSuccess() {
        repository.loadError()

        viewModel.init(category = "category 1")
        communication.checkUiState(ProductsUiState.Progress)

        runAsync.pingResult()
        mapper.checkMappedError(expectedMessage = "Problems")

        repository.loadSuccess()

        viewModel.retry(category = "category 1")
        runAsync.pingResult()
        mapper.checkMappedSuccess(
            expectedItems = listOf(
                ProductItem.Base(
                    id = 1,
                    title = "product 1",
                    price = 1.0,
                    description = "this 1",
                    category = "category 1",
                    imageUrl = "url/image1",
                    rate = 5.0,
                    count = 4,
                    favorite = false,
                    addedToCart = false
                ),
                ProductItem.Base(
                    id = 2,
                    title = "product 2",
                    price = 2.0,
                    description = "this 2",
                    category = "category 1",
                    imageUrl = "url/image2",
                    rate = 5.0,
                    count = 4,
                    favorite = false,
                    addedToCart = false
                ),
            )
        )
    }

    @Test
    fun testFindProductById() {
        repository.loadSuccess()

        viewModel.product(1)

        runAsync.pingResult()
        productUiCommunication.checkUi(
            ProductUi.Base(
                id = 1,
                title = "product 1",
                price = 1.0,
                description = "this 1",
                category = "category 1",
                imageUrl = "url/image1",
                rate = 5.0,
                count = 4,
                favorite = false,
                addedToCart = false
            )
        )
    }

    @Test
    fun testGoToProductDetails() {
        viewModel.goToProductsDetails(id = 1)
        navigation.checkScreen(ProductDetailsScreen(productId = 1))
    }

    @Test
    fun testGoToProductsAndBack() {
        viewModel.goToCategories()
        navigation.checkScreen(Screen.Pop)
    }

    @Test
    fun testChangeFavoriteAndAddedToCart() = runBlocking {
        repository.loadSuccess()

        viewModel.changeAddedToFavorites(1)
        assertEquals(
            LoadResult.Success(
                items = mutableListOf(
                    ProductItem.Base(
                        id = 1,
                        title = "product 1",
                        price = 1.0,
                        description = "this 1",
                        category = "category 1",
                        imageUrl = "url/image1",
                        rate = 5.0,
                        count = 4,
                        favorite = true,
                        addedToCart = false
                    ),
                    ProductItem.Base(
                        id = 2,
                        title = "product 2",
                        price = 2.0,
                        description = "this 2",
                        category = "category 1",
                        imageUrl = "url/image2",
                        rate = 5.0,
                        count = 4,
                        favorite = false,
                        addedToCart = false
                    )
                )
            ),
            repository.products("category 1")
        )

        viewModel.changeAddedToCart(2)

        assertEquals(
            LoadResult.Success(
                items = mutableListOf(
                    ProductItem.Base(
                        id = 1,
                        title = "product 1",
                        price = 1.0,
                        description = "this 1",
                        category = "category 1",
                        imageUrl = "url/image1",
                        rate = 5.0,
                        count = 4,
                        favorite = true,
                        addedToCart = false
                    ),
                    ProductItem.Base(
                        id = 2,
                        title = "product 2",
                        price = 2.0,
                        description = "this 2",
                        category = "category 1",
                        imageUrl = "url/image2",
                        rate = 5.0,
                        count = 4,
                        favorite = false,
                        addedToCart = true
                    )
                )
            ),
            repository.products("category 1")
        )

        runAsync.pingResult()
        cartBadgeLiveDataWrapper.checkUpdatedValue(1)

    }

}


private class FakeProductsRepository : ProductsRepository {

    private var listProduct: MutableList<ProductItem> = mutableListOf(
        ProductItem.Base(
            id = 1,
            title = "product 1",
            price = 1.0,
            description = "this 1",
            category = "category 1",
            imageUrl = "url/image1",
            rate = 5.0,
            count = 4,
            favorite = false,
            addedToCart = false
        ),
        ProductItem.Base(
            id = 2,
            title = "product 2",
            price = 2.0,
            description = "this 2",
            category = "category 1",
            imageUrl = "url/image2",
            rate = 5.0,
            count = 4,
            favorite = false,
            addedToCart = false
        )
    )

    private lateinit var result: LoadResult<ProductItem>

    fun loadSuccess() {
        result = LoadResult.Success(listProduct)
    }

    fun loadError() {
        result = LoadResult.Error(message = "Problems")
    }

    override suspend fun products(category: String): LoadResult<ProductItem> {
        return result
    }

    override suspend fun product(id: Int): ProductItem {
        return if (id == 1) ProductItem.Base(
            id = 1,
            title = "product 1",
            price = 1.0,
            description = "this 1",
            category = "category 1",
            imageUrl = "url/image1",
            rate = 5.0,
            count = 4,
            favorite = false,
            addedToCart = false
        ) else ProductItem.Base(
            id = 2,
            title = "product 2",
            price = 2.0,
            description = "this 2",
            category = "category 1",
            imageUrl = "url/image2",
            rate = 5.0,
            count = 4,
            favorite = false,
            addedToCart = false
        )
    }

    var sizeCart = 0

    override suspend fun changeAddedToCart(id: Int): Int {
        if (id == 1) {
            listProduct[0] = ProductItem.Base(
                id = 1,
                title = "product 1",
                price = 1.0,
                description = "this 1",
                category = "category 1",
                imageUrl = "url/image1",
                rate = 5.0,
                count = 4,
                favorite = false,
                addedToCart = true
            )
        } else {
            listProduct[1] = ProductItem.Base(
                id = 2,
                title = "product 2",
                price = 2.0,
                description = "this 2",
                category = "category 1",
                imageUrl = "url/image2",
                rate = 5.0,
                count = 4,
                favorite = false,
                addedToCart = true
            )
        }
        return ++sizeCart
    }

    override suspend fun changeFavorite(id: Int) {
        if (id == 1) {
            listProduct[0] = ProductItem.Base(
                id = 1,
                title = "product 1",
                price = 1.0,
                description = "this 1",
                category = "category 1",
                imageUrl = "url/image1",
                rate = 5.0,
                count = 4,
                favorite = true,
                addedToCart = false
            )
        } else {
            listProduct[1] = ProductItem.Base(
                id = 2,
                title = "product 2",
                price = 2.0,
                description = "this 2",
                category = "category 1",
                imageUrl = "url/image2",
                rate = 5.0,
                count = 4,
                favorite = true,
                addedToCart = false
            )
        }

    }
}

internal class FakeMapper() : LoadResult.Mapper<ProductItem> {

    private var actualItems = emptyList<ProductItem>()

    private var actualMessage = ""


    override fun mapSuccess(items: List<ProductItem>) {
        actualItems = items
    }

    fun checkMappedSuccess(expectedItems: List<ProductItem>) =
        assertEquals(expectedItems, actualItems)

    override fun mapError(message: String) {
        actualMessage = message
    }

    fun checkMappedError(expectedMessage: String) = assertEquals(actualMessage, expectedMessage)

}

private class FakeCartBadgeLiveDataWrapper() : UiUpdate<Int>, CartBadgeLiveDataWrapper.Update {

    private var actualValue = -1

    override fun updateUi(value: Int) {
        actualValue = value
    }

    fun checkUpdatedValue(expectedValue: Int) = assertEquals(expectedValue, actualValue)
}

private class FakeProductUiCommunication() : ProductCommunication {

    private var storage: ProductUi = ProductUi.Base(
        id = 1,
        title = "product 1",
        price = 1.0,
        description = "this 1",
        category = "category 1",
        imageUrl = "url/image1",
        rate = 5.0,
        count = 4,
        favorite = false,
        addedToCart = false
    )

    override fun liveData(): LiveData<ProductUi> {
        throw IllegalAccessException("Don`t use in unit test")
    }

    override fun updateUi(value: ProductUi) {
        storage = value
    }

    fun checkUi(productUi: ProductUi) {
        assertEquals(storage, productUi)
    }
}

private class FakeProductsCommunication : ProductsCommunication {

    private var actualUiState: ProductsUiState = ProductsUiState.Empty

    override fun updateUi(value: ProductsUiState) {
        actualUiState = value
    }

    override fun liveData(): LiveData<ProductsUiState> {
        throw IllegalAccessException("Don`t use in test")
    }

    fun checkUiState(uiState: ProductsUiState) {
        assertEquals(uiState, actualUiState)
    }
}

private class FakeProductItemToProductUiMapper() : ProductItem.Mapper<ProductUi> {
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
    ): ProductUi = ProductUi.Base(
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