package com.example.fakestore.di

import com.example.fakestore.content.categories.data.HandleError
import com.example.fakestore.core.presentation.RunAsync
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun bindRunAsync(runAsync: RunAsync.Base): RunAsync

    @Binds
    abstract fun bindHandleError(handleError: HandleError.Base): HandleError
}