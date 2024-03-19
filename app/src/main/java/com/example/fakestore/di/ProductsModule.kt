package com.example.fakestore.di

import com.example.fakestore.content.products.data.BaseProductsRepository
import com.example.fakestore.content.products.data.cache.ProductsCacheDataSource
import com.example.fakestore.content.products.data.cloud.ProductsCloudDataSource
import com.example.fakestore.content.products.data.cloud.ProductsService
import com.example.fakestore.content.products.domain.ProductItem
import com.example.fakestore.content.products.domain.ProductsRepository
import com.example.fakestore.content.products.presentation.BaseProductsLoadResultMapper
import com.example.fakestore.content.products.presentation.ProductItemToProductUiMapper
import com.example.fakestore.content.products.presentation.ProductPositionLiveDataWrapper
import com.example.fakestore.content.products.presentation.ProductsCommunication
import com.example.fakestore.content.products.presentation.adapter.ProductUi
import com.example.fakestore.core.ProvideLiveData
import com.example.fakestore.core.domain.LoadResult
import com.example.fakestore.main.CartBadgeLiveDataWrapper
import com.example.fakestore.main.CartBadgeStorage
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

    @Binds
    @ViewModelScoped
    abstract fun bindCartBadgeLiveData(liveData: CartBadgeLiveDataWrapper.Mutable): CartBadgeLiveDataWrapper.Update


    @Binds
    @ViewModelScoped
    abstract fun bindProductPositionLiveData(liveData: ProductPositionLiveDataWrapper.Mutable): ProductPositionLiveDataWrapper.Provide

    @Binds
    @ViewModelScoped
    abstract fun bindCartBadgeStorageSave(storage: CartBadgeStorage.Base): CartBadgeStorage.Save

    @Binds
    @ViewModelScoped
    abstract fun bindProductPositionLiveDataWrapper(liveDataWrapper: ProvideLiveData<Int>): ProvideLiveData<Int>

    @Binds
    @ViewModelScoped
    abstract fun bindCacheDataSource(cacheDataSource: ProductsCacheDataSource.Base): ProductsCacheDataSource

    companion object {

        @Provides
        @ViewModelScoped
        fun provideProductsCloudDataSource(retrofit: Retrofit): ProductsCloudDataSource =
            ProductsCloudDataSource.Base(retrofit.create(ProductsService::class.java))

    }
}