package com.example.fakestore.di

import com.example.fakestore.cart.presentation.OrderCommunication
import com.example.fakestore.ordershistory.data.BaseOrdersRepository
import com.example.fakestore.ordershistory.data.OrdersHistoryCacheDataSource
import com.example.fakestore.ordershistory.domain.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class OrdersHistoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCacheDataSource(cacheDataSource: OrdersHistoryCacheDataSource.Base): OrdersHistoryCacheDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindOrdersRepository(repository: BaseOrdersRepository): OrdersRepository

    @Binds
    @ViewModelScoped
    abstract fun bindCommunicationOrders(communication: OrderCommunication.Base): OrderCommunication
}