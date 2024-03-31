package com.example.fakestore.content.products.presentation

import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.content.products.presentation.adapter.ProductUi
import com.example.fakestore.core.domain.LoadResult
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