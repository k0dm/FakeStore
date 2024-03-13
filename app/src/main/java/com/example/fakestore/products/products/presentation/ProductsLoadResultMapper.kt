package com.example.fakestore.products.products.presentation

import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.products.products.domain.ProductItem
import javax.inject.Inject
import javax.inject.Singleton

interface ProductsLoadResultMapper : LoadResult.Mapper<ProductItem>

@Singleton
class BaseProductsLoadResultMapper @Inject constructor(
    private val communication: ProductsCommunication,
    private val productItemToProductUiMapper: ProductItemToProductUiMapper
) : ProductsLoadResultMapper {

    override fun mapSuccess(items: List<ProductItem>) {
        communication.updateUi(ProductsUiState.Success(items.map {
            it.map(productItemToProductUiMapper)
        }))
    }

    override fun mapError(message: String) {
        communication.updateUi(ProductsUiState.Error(message))
    }
}