package com.example.fakestore.di

import com.example.fakestore.content.details.data.BaseProductsDetailsRepository
import com.example.fakestore.content.details.domain.ProductsDetailsRepository
import com.example.fakestore.content.details.presentation.ProductDetailsCommunication
import com.example.fakestore.content.details.presentation.ProductsDetailsUiModel
import com.example.fakestore.content.details.presentation.ToProductsDetailsUiModelMapper
import com.example.fakestore.content.products.domain.ProductItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductsDetailModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCommunication(communication: ProductDetailsCommunication.Base): ProductDetailsCommunication

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repository: BaseProductsDetailsRepository): ProductsDetailsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMapper(mapper: ToProductsDetailsUiModelMapper): ProductItem.Mapper<ProductsDetailsUiModel>
}