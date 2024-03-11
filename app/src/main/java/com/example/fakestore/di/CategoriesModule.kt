package com.example.fakestore.di

import com.example.fakestore.products.categories.data.BaseCategoriesRepository
import com.example.fakestore.products.categories.domain.CategoriesRepository
import com.example.fakestore.products.categories.domain.LoadCategoriesResult
import com.example.fakestore.products.categories.presentation.BaseLoadCategoriesResultMapper
import com.example.fakestore.products.categories.presentation.CategoriesCommunication
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesModule() {

    @Binds
    abstract fun bindMapper(mapper: BaseLoadCategoriesResultMapper): LoadCategoriesResult.Mapper

    @Binds
    abstract fun bindCategoriesCommunication(communication: CategoriesCommunication.Base): CategoriesCommunication

    @Binds
    abstract fun bindCategoriesRepository(repository: BaseCategoriesRepository): CategoriesRepository
}