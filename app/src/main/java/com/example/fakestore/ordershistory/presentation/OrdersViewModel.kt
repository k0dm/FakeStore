package com.example.fakestore.ordershistory.presentation

import com.example.fakestore.cart.presentation.OrderCommunication
import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.presentation.RunAsync
import com.example.fakestore.ordershistory.domain.OrdersRepository
import com.example.fakestore.ordershistory.presentation.adapter.orders.OrderUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: OrdersRepository,
    private val communication: OrderCommunication,
    runAsync: RunAsync
) : BaseViewModel(runAsync) {

    fun liveData() = communication.liveData()


    fun init() {
        runAsync({
            repository.orders()
        }) {
            communication.updateUi(
                if (it.isEmpty()) {
                    OrdersUiState.NoOrders
                } else {
                    OrdersUiState.Base(it.map {
                        OrderUi.Base(
                            number = it.number,
                            date = it.date,
                            totalPrice = it.totalPrice,
                            products = it.products
                        )
                    })
                }
            )
        }
    }

}