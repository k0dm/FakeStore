package com.example.fakestore.di

import com.example.fakestore.cart.data.BaseCartRepository
import com.example.fakestore.cart.data.GenerateId
import com.example.fakestore.cart.data.LocalDate
import com.example.fakestore.cart.data.cache.CartCacheDataSource
import com.example.fakestore.cart.domain.CartRepository
import com.example.fakestore.cart.presentation.CartCommunication
import com.example.fakestore.cart.presentation.SubtotalLiveDataWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CartModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCacheDataSource(cacheDataSource: CartCacheDataSource.Base): CartCacheDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindFavoriteProductsCommunication(communication: CartCommunication.Base): CartCommunication

    @Binds
    @ViewModelScoped
    abstract fun bindCartRepository(repository: BaseCartRepository): CartRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGenerateId(generateId: GenerateId.Base): GenerateId

    @Binds
    @ViewModelScoped
    abstract fun bindLocalDate(localDate: LocalDate.Base): LocalDate

    @Binds
    @ViewModelScoped
    abstract fun bindSubtotalLiveDataWrapper(liveDataWrapper: SubtotalLiveDataWrapper.Base): SubtotalLiveDataWrapper
}