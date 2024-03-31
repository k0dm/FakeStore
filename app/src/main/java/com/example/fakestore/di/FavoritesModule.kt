package com.example.fakestore.di

import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.favorites.data.BaseFavoritesRepository
import com.example.fakestore.favorites.data.ProductFavoritesCacheDataSource
import com.example.fakestore.favorites.domain.FavoritesRepository
import com.example.fakestore.favorites.presentation.FavoriteProductsCommunication
import com.example.fakestore.favorites.presentation.ProductItemToProductFavoriteUiMapper
import com.example.fakestore.favorites.presentation.adapter.ProductFavoriteUi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoritesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCacheDataSource(cacheDataSource: ProductFavoritesCacheDataSource.Base): ProductFavoritesCacheDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindFavoriteProductsCommunication(communication: FavoriteProductsCommunication.Base): FavoriteProductsCommunication

    @Binds
    @ViewModelScoped
    abstract fun bindFavoritesRepository(repository: BaseFavoritesRepository): FavoritesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindProductItemToProductFavoriteUiMapper(mapper: ProductItemToProductFavoriteUiMapper): ProductItem.Mapper<ProductFavoriteUi>
}