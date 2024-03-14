package com.example.fakestore.products.products.data.cache

interface ProductsCacheDataSource {

    suspend fun products(): List<ProductEntity>

    suspend fun saveProducts(products: List<ProductEntity>)

    suspend fun changeItemFavorite(id: Int)

    suspend fun changeItemAddedToCart(id: Int)

    class Base(private val dao: ProductsDao) : ProductsCacheDataSource {

        override suspend fun products(): List<ProductEntity> = dao.products()

        override suspend fun saveProducts(products: List<ProductEntity>) {
            dao.saveProducts(products)
        }

        override suspend fun changeItemFavorite(id: Int) {
            val productEntity: ProductEntity = dao.product(id)
            val newProducts = productEntity.copy(favorite = !productEntity.favorite)
            dao.saveProducts(listOf(newProducts))
        }

        override suspend fun changeItemAddedToCart(id: Int) {
            val productEntity: ProductEntity = dao.product(id)
            val newProducts = productEntity.copy(addedToCart = !productEntity.addedToCart)
            dao.saveProducts(listOf(newProducts))
        }
    }
}

