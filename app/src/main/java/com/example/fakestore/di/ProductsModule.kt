package com.example.fakestore.di

import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.products.products.data.BaseProductsRepository
import com.example.fakestore.products.products.data.cloud.ProductsCloudDataSource
import com.example.fakestore.products.products.data.cloud.ProductsService
import com.example.fakestore.products.products.domain.ProductItem
import com.example.fakestore.products.products.domain.ProductsRepository
import com.example.fakestore.products.products.presentation.BaseProductsLoadResultMapper
import com.example.fakestore.products.products.presentation.ProductItemToProductUiMapper
import com.example.fakestore.products.products.presentation.ProductsCommunication
import com.example.fakestore.products.products.presentation.adapter.ProductUi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductsModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCommunication(communication: ProductsCommunication.Base): ProductsCommunication

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repository: BaseProductsRepository): ProductsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindItemUiMapper(mapper: ProductItemToProductUiMapper): ProductItem.Mapper<ProductUi>

    @Binds
    @ViewModelScoped
    abstract fun bindResultMapper(mapper: BaseProductsLoadResultMapper): LoadResult.Mapper<ProductItem>

    companion object {

        @Provides
        @ViewModelScoped
        fun provideProductsCloudDataSource(retrofit: Retrofit): ProductsCloudDataSource =
            ProductsCloudDataSource.Base(retrofit.create(ProductsService::class.java))
    }
}