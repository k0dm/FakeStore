package com.example.fakestore.di

import com.example.fakestore.content.categories.data.BaseCategoriesRepository
import com.example.fakestore.content.categories.domain.CategoriesRepository
import com.example.fakestore.content.categories.presentation.BaseCategoriesLoadResultMapper
import com.example.fakestore.content.categories.presentation.CategoriesCommunication
import com.example.fakestore.content.categories.presentation.CategoriesLoadResultMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesModule() {

    @Binds
    abstract fun bindMapper(mapper: BaseCategoriesLoadResultMapper): CategoriesLoadResultMapper

    @Binds
    abstract fun bindCategoriesCommunication(communication: CategoriesCommunication.Base): CategoriesCommunication

    @Binds
    abstract fun bindCategoriesRepository(repository: BaseCategoriesRepository): CategoriesRepository
}