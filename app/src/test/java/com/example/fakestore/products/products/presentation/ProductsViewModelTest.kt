package com.example.fakestore.products.products.presentation

import androidx.lifecycle.LiveData
import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.core.FakeRunAsync
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.main.Screen
import com.example.fakestore.products.products.domain.ProductItem
import com.example.fakestore.products.products.domain.ProductsRepository
import com.example.fakestore.products.products.presentation.adapter.ProductUi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductsViewModelTest {

    private lateinit var viewModel: ProductsViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var communication: FakeProductsCommunication
    private lateinit var repository: FakeProductsRepository
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setUp() {
        navigation = FakeNavigation.Base()
        communication = FakeProductsCommunication()
        repository = FakeProductsRepository()
        runAsync = FakeRunAsync()
        viewModel = ProductsViewModel(
            navigation = navigation,
            communication = communication,
            repository = repository,
            runAsync = runAsync
        )
    }

    @Test
    fun testInitLoadSuccess() {
        repository.loadSuccess()

        viewModel.init(category = "category 1")
        communication.checkUiState(ProductsUiState.Progress)

        runAsync.pingResult()
        communication.checkUiState(
            ProductsUiState.Success(
                products = listOf(
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
                    ),
                    ProductUi.Base(
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
        )
    }

    @Test
    fun testInitLoadError() {
        repository.loadError()

        viewModel.init(category = "category 1")
        communication.checkUiState(ProductsUiState.Progress)

        runAsync.pingResult()
        communication.checkUiState(ProductsUiState.Error(message = "Problems"))
    }

    @Test
    fun testInitErrorThenSuccess() {
        repository.loadError()

        viewModel.init(category = "category 1")
        communication.checkUiState(ProductsUiState.Progress)

        runAsync.pingResult()
        communication.checkUiState(ProductsUiState.Error(message = "Problems"))

        repository.loadSuccess()

        viewModel.retry(category = "category 1")
        runAsync.pingResult()
        communication.checkUiState(
            ProductsUiState.Success(
                products = listOf(
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
                    ),
                    ProductUi.Base(
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

        repository.changeAddedToCart(2)
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

    override suspend fun changeAddedToCart(id: Int) {
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
