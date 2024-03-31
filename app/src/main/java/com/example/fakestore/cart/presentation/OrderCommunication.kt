package com.example.fakestore.cart.presentation

import com.example.fakestore.core.presentation.LiveDataWrapper
import com.example.fakestore.ordershistory.presentation.OrdersUiState
import javax.inject.Inject

interface OrderCommunication : LiveDataWrapper<OrdersUiState> {

    class Base @Inject constructor() : OrderCommunication, LiveDataWrapper.Single<OrdersUiState>()
}