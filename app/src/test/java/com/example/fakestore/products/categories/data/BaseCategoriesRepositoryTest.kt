package com.example.fakestore.products.categories.data

import com.example.fakestore.core.FakeHandleError
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.products.categories.data.cache.CategoriesCacheDataSource
import com.example.fakestore.products.categories.data.cloud.CategoriesCloudDataSource
import com.example.fakestore.products.categories.domain.CategoriesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseCategoriesRepositoryTest {

    private lateinit var repository: CategoriesRepository
    private lateinit var cloudDataSource: FakeCloudDataSource
    private lateinit var cacheDataSource: FakeCacheDataSource
    private lateinit var handleError: FakeHandleError

    @Before
    fun setUp() {
        cloudDataSource = FakeCloudDataSource()
        cacheDataSource = FakeCacheDataSource()
        handleError = FakeHandleError()
        repository = BaseCategoriesRepository(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            handleError = handleError,
        )
    }

    @Test
    fun testNoCacheAndLoadSuccess(): Unit = runBlocking {
        cacheDataSource.noCache()

        val actualLoadResult = repository.categories()
        assertEquals(
            LoadResult.Success(items = listOf("1", "2", "3")),
            actualLoadResult
        )
        cacheDataSource.checkSavedCategories(listOf("1", "2", "3"))
    }

    @Test
    fun testNoCacheAndLoadError(): Unit = runBlocking {
        cacheDataSource.noCache()
        cloudDataSource.loadError()

        val actualLoadResult = repository.categories()
        assertEquals(
            LoadResult.Error<String>(message = "Problems"),
            actualLoadResult
        )
        cacheDataSource.checkSavedCategories(emptyList())
    }

    @Test
    fun testHaveCacheAndLoadSuccess(): Unit = runBlocking {
        val actualLoadResult = repository.categories()
        assertEquals(
            LoadResult.Success(items = listOf("4", "5", "6")),
            actualLoadResult
        )
    }
}

private class FakeCacheDataSource() : CategoriesCacheDataSource {

    private var hasCache = true

    override suspend fun categories(): List<String> {
        return if (hasCache) listOf("4", "5", "6") else emptyList()
    }

    fun noCache() {
        hasCache = false
    }

    private var cache: List<String> = emptyList()

    override suspend fun saveCategories(categories: List<String>) {
        cache = categories
    }

    fun checkSavedCategories(expected: List<String>) {
        assertEquals(expected, cache)
    }
}

private class FakeCloudDataSource() : CategoriesCloudDataSource {

    private var loadSuccess = true

    override suspend fun loadCategories(): List<String> {
        return if (loadSuccess) listOf("1", "2", "3") else throw IllegalAccessException()
    }

    fun loadError() {
        loadSuccess = false
    }
}

