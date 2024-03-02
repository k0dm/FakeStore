package com.example.fakestore.products.categories.presentation

import androidx.lifecycle.LiveData
import com.example.fakestore.core.FakeNavigation
import com.example.fakestore.core.FakeNavigationUpdate
import com.example.fakestore.core.FakeRunAsync
import com.example.fakestore.products.categories.domain.CategoriesRepository
import com.example.fakestore.products.categories.domain.LoadCategoriesResult
import com.example.fakestore.products.products.presentation.ProductsScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CategoriesViewModelTest {

    private lateinit var viewModel: CategoriesViewModel
    private lateinit var navigation: FakeNavigationUpdate
    private lateinit var communication: FakeCategoriesCommunication
    private lateinit var repository: FakeCategoriesRepository
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setUp() {
        navigation = FakeNavigation.Base()
        communication = FakeCategoriesCommunication()
        repository = FakeCategoriesRepository()
        runAsync = FakeRunAsync()
        viewModel = CategoriesViewModel(
            navigation = navigation,
            communication = communication,
            repository = repository,
            runAsync = runAsync
        )
    }


    @Test
    fun testInitLoadError() {
        repository.loadError()

        viewModel.init()
        communication.checkUiState(CategoriesUiState.Progress)

        runAsync.pingResult()
        communication.checkUiState(CategoriesUiState.Error(message = "Problems"))
    }

    @Test
    fun testInitLoadSuccess() {
        viewModel.init()
        communication.checkUiState(CategoriesUiState.Progress)

        runAsync.pingResult()
        communication.checkUiState(CategoriesUiState.Success(categories = listOf("1", "2")))
    }

    @Test
    fun testInitErrorThenSuccess() {
        repository.loadError()

        viewModel.init()
        communication.checkUiState(CategoriesUiState.Progress)

        runAsync.pingResult()
        communication.checkUiState(CategoriesUiState.Error(message = "Problems"))

        repository.loadSuccess()

        viewModel.retry()
        communication.checkUiState(CategoriesUiState.Progress)
        runAsync.pingResult()
        communication.checkUiState(CategoriesUiState.Success(categories = listOf("3", "4")))
    }

    @Test
    fun testGoToProducts() {
        viewModel.goToProducts(category = "3")
        navigation.checkScreen(ProductsScreen(category = "3"))
    }
}


private class FakeCategoriesCommunication : CategoriesCommunication {

    private var actualUiState: CategoriesUiState = CategoriesUiState.Empty

    override fun updateUi(value: CategoriesUiState) {
        actualUiState = value
    }

    fun checkUiState(expected: CategoriesUiState) {
        assertEquals(expected, actualUiState)
    }

    override fun liveData(): LiveData<CategoriesUiState> {
        throw IllegalAccessException("Don`t use in unit test")
    }
}

private class FakeCategoriesRepository() : CategoriesRepository {

    private var loadResult: LoadCategoriesResult =
        LoadCategoriesResult.Success(categories = listOf<String>("1", "2"))

    override suspend fun loadCategories(): LoadCategoriesResult {
        return loadResult
    }

    fun loadError() {
        loadResult = LoadCategoriesResult.Error(message = "Problems")
    }

    fun loadSuccess() {
        loadResult = LoadCategoriesResult.Success(categories = listOf<String>("3", "4"))
    }
}