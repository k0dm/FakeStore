package com.example.fakestore.products.products.presentation

import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.products.products.domain.ProductItem
import com.example.fakestore.products.products.presentation.adapter.ProductUi
import javax.inject.Inject

class BaseProductsLoadResultMapper @Inject constructor(
    private val communication: ProductsCommunication,
    private val productItemToProductUiMapper: ProductItem.Mapper<ProductUi>
) : LoadResult.Mapper<ProductItem> {

    override fun mapSuccess(items: List<ProductItem>) {
        communication.updateUi(ProductsUiState.Success(items.map {
            it.map(productItemToProductUiMapper)
        }))
    }

    override fun mapError(message: String) {
        communication.updateUi(ProductsUiState.Error(message))
    }
}