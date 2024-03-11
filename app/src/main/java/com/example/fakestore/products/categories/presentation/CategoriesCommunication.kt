package com.example.fakestore.products.categories.presentation

import com.example.fakestore.core.LiveDataWrapper
import javax.inject.Inject
import javax.inject.Singleton

interface CategoriesCommunication : LiveDataWrapper<CategoriesUiState> {

    @Singleton
    class Base @Inject constructor() : CategoriesCommunication, LiveDataWrapper.Single<CategoriesUiState>()
}