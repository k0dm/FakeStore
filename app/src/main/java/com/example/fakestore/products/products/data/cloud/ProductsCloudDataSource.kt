package com.example.fakestore.products.products.data.cloud

interface ProductsCloudDataSource {

    suspend fun loadProducts(category: String): List<ProductResponse>

    class Base(private val service: ProductsService) : ProductsCloudDataSource {

        override suspend fun loadProducts(category: String): List<ProductResponse> =
            service.loadProducts(category).execute().body()!!
    }
}

