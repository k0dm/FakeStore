package com.example.fakestore.products.categories.presentation

import com.example.fakestore.core.LiveDataWrapper

interface CategoriesCommunication : LiveDataWrapper<CategoriesUiState> {

    class Base() : CategoriesCommunication, LiveDataWrapper.Single<CategoriesUiState>()
}