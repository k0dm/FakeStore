package com.example.fakestore.content.products.presentation

import com.example.fakestore.content.products.presentation.adapter.ProductUi
import com.example.fakestore.core.presentation.LiveDataWrapper
import javax.inject.Inject

interface ProductCommunication : LiveDataWrapper<ProductUi> {

    class Base @Inject constructor() : ProductCommunication, LiveDataWrapper.Single<ProductUi>()
}