package com.example.fakestore.di

import com.example.fakestore.main.CartBadgeLiveDataWrapper
import com.example.fakestore.main.CartBadgeStorage
import com.example.fakestore.main.Navigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    abstract fun bindsNavigationNavigate(navigation: Navigation.Base): Navigation.Navigate

    @Binds
    abstract fun bindsNavigationMutable(navigation: Navigation.Base): Navigation.Mutable

    @Binds
    @Singleton
    abstract fun bindsLiveData(liveData: CartBadgeLiveDataWrapper.Base): CartBadgeLiveDataWrapper

    @Binds
    abstract fun bindCartBadgeStorageRead(storage: CartBadgeStorage.Base): CartBadgeStorage.Read
}