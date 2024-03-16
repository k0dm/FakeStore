package com.example.fakestore.products.products.presentation

import androidx.lifecycle.LiveData
import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.core.FakeRunAsync
import com.example.fakestore.core.UiUpdate
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.main.Screen
import com.example.fakestore.products.products.domain.ProductItem
import com.example.fakestore.products.products.domain.ProductsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductsViewModelTest {

    private lateinit var viewModel: ProductsViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var communication: FakeProductsCommunication
    private lateinit var cartBadgeLiveDataWrapper: FakeCartBadgeLiveDataWrapper
    private lateinit var repository: FakeProductsRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var mapper: FakeMapper

    @Before
    fun setUp() {
        navigation = FakeNavigation.Base()
        communication = FakeProductsCommunication()
        repository = FakeProductsRepository()
        cartBadgeLiveDataWrapper = FakeCartBadgeLiveDataWrapper()
        mapper = FakeMapper()
        runAsync = FakeRunAsync()
        viewModel = ProductsViewModel(
            navigation = navigation,
            communication = communication,
            cartBadgeLiveData = cartBadgeLiveDataWrapper,
            repository = repository,
            mapper = mapper,
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
    fun testGoToProductDetails() {
        viewModel.goToProductsDetails(id = 1, category = "category 1")
        navigation.checkScreen(ProductDetailsScreen(productId = 1, category = "category 1"))
    }

    @Test
    fun testGoToProductDetailsAndBack() {
        viewModel.goToProductsDetails(id = 1, category = "category 1")
        navigation.checkScreen(ProductDetailsScreen(productId = 1, category = "category 1"))

        viewModel.goToCategories()
        navigation.checkScreen(Screen.Pop)
    }

    @Test
    fun testChangeFavoriteAndAddedToCart() = runBlocking {
        repository.loadSuccess()

        repository.changeFavorite(1)
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

        val size = repository.changeAddedToCart(2)
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
        assertEquals(1, size)
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

private class FakeMapper() : LoadResult.Mapper<ProductItem> {

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

private class FakeCartBadgeLiveDataWrapper() : UiUpdate<Int> {

    private var actualValue = -1

    override fun updateUi(value: Int) {
        actualValue = value
    }

    fun checkUpdatedValue(expectedValue: Int) = assertEquals(expectedValue, actualValue)
}