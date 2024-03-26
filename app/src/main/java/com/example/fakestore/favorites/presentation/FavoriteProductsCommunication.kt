package com.example.fakestore.favorites.presentation

import com.example.fakestore.core.presentation.LiveDataWrapper
import javax.inject.Inject

interface FavoriteProductsCommunication : LiveDataWrapper<ProductFavoriteUiState> {

    class Base @Inject constructor() : FavoriteProductsCommunication,
        LiveDataWrapper.Single<ProductFavoriteUiState>()
}