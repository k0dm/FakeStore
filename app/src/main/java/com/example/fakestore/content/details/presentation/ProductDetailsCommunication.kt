package com.example.fakestore.content.details.presentation

import com.example.fakestore.core.presentation.LiveDataWrapper
import javax.inject.Inject

interface ProductDetailsCommunication : LiveDataWrapper<ProductsDetailsUiModel> {

    class Base @Inject constructor() : ProductDetailsCommunication,
        LiveDataWrapper.Single<ProductsDetailsUiModel>()
}