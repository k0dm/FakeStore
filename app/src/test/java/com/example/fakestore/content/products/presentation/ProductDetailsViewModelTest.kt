package com.example.fakestore.content.products.presentation


import com.example.fakestore.content.details.domain.ProductsDetailsRepository
import com.example.fakestore.content.details.presentation.ProductDetailsCommunication
import com.example.fakestore.content.details.presentation.ProductDetailsViewModel
import com.example.fakestore.content.details.presentation.ProductsDetailsUiModel
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.core.FakeRunAsync
import com.example.fakestore.main.FakeCartBadgeLiveDataWrapper
import com.example.fakestore.main.Screen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductDetailsViewModelTest {

    private lateinit var viewModel: ProductDetailsViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var communication: FakeProductsDetailsCommunication
    private lateinit var repository: FakeProductsDetailsRepository
    private lateinit var cartBadgeLiveDataWrapper: FakeCartBadgeLiveDataWrapper
    private lateinit var runAsync: FakeRunAsync
    private lateinit var mapper: FakeMapperProductDetails

    @Before
    fun setUp() {
        navigation = FakeNavigation.Base()
        communication = FakeProductsDetailsCommunication()
        repository = FakeProductsDetailsRepository()
        cartBadgeLiveDataWrapper = FakeCartBadgeLiveDataWrapper()
        mapper = FakeMapperProductDetails()
        runAsync = FakeRunAsync()
        viewModel = ProductDetailsViewModel(
            navigation = navigation,
            communication = communication,
            cartBadgeLiveDataWrapper = cartBadgeLiveDataWrapper,
            repository = repository,
            mapper = mapper,
            runAsync = runAsync
        )
    }

    @Test
    fun testInit() {
        viewModel.init(id = 1)
        runAsync.pingResult()
        communication.checkUpdatedUi(
            ProductsDetailsUiModel(
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
    fun testBackToProducts() {
        viewModel.goToProducts()
        navigation.checkScreen(Screen.Pop)
    }

    @Test
    fun testChangeAddedToCart() {
        viewModel.changeAddedToCart(id = 1)
        runAsync.pingResult()
        cartBadgeLiveDataWrapper.checkUpdatedValue(1)
    }

    @Test
    fun testChangeFavorite() {
        viewModel.changeFavorite(id = 1)
        runAsync.pingResult()

        ProductsDetailsUiModel(
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
    }
}

private class FakeMapperProductDetails() : ProductItem.Mapper<ProductsDetailsUiModel> {
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
    ): ProductsDetailsUiModel = ProductsDetailsUiModel(
        id, title, price, description, category, imageUrl, rate, count, favorite, addedToCart
    )
}

private class FakeProductsDetailsRepository : ProductsDetailsRepository {

    private var cache = ProductItem.Base(
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

    override suspend fun product(id: Int): ProductItem {
        return cache
    }

    override suspend fun changeFavorite(id: Int) {
        if (id == 1) {
            val oldCache = cache
            val newCache = cache.copy(favorite = true)
            cache = newCache
        }
    }

    override suspend fun changeAddedToCart(id: Int): Int {
        if (id == 1) {
            val oldCache = cache
            val newCache = cache.copy(addedToCart = true)
            cache = newCache
        }
        return 1
    }
}

private class FakeProductsDetailsCommunication() : ProductDetailsCommunication {

    private lateinit var cacheUiModel: ProductsDetailsUiModel

    override fun updateUi(value: ProductsDetailsUiModel) {
        cacheUiModel = value
    }

    fun checkUpdatedUi(expectedUi: ProductsDetailsUiModel) {
        assertEquals(expectedUi, cacheUiModel)
    }

    override fun liveData() = throw IllegalAccessException("Don`t use in unit test")
}