package com.example.fakestore.cart.presentation

import com.example.fakestore.core.presentation.LiveDataWrapper
import javax.inject.Inject

interface CartCommunication : LiveDataWrapper<CartUiState> {

    class Base @Inject constructor() : CartCommunication, LiveDataWrapper.Single<CartUiState>()
}