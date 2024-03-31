package com.example.fakestore.di

import com.example.fakestore.ordershistory.data.OrdersHistoryCacheDataSource
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
}