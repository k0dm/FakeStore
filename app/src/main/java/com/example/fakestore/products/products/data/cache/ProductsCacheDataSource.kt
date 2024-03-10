package com.example.fakestore.products.products.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    suspend fun products(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun product(id: Int): ProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE favorite = 1")
    suspend fun favoriteProduct(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE added_to_cart = 1")
    suspend fun addedToCartProducts(): List<ProductEntity>
}