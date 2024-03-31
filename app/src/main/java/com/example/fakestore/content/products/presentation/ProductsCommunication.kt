package com.example.fakestore.content.products.presentation

import com.example.fakestore.core.presentation.LiveDataWrapper
import javax.inject.Inject

interface ProductsCommunication : LiveDataWrapper<ProductsUiState> {

    class Base @Inject constructor() : ProductsCommunication,
        LiveDataWrapper.Single<ProductsUiState>()
}