package com.example.fakestore.di

import android.content.Context
import androidx.room.Room
import com.example.fakestore.core.data.FakeStoreDatabase
import com.example.fakestore.products.categories.data.cache.CategoriesCacheDataSource
import com.example.fakestore.products.categories.data.cloud.CategoriesCloudDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FakeStoreDatabase = Room
        .databaseBuilder(
            context,
            FakeStoreDatabase::class.java,
            "fake_store_db"
        ).build()

    @Provides
    fun provideCategoriesCacheDataSource(database: FakeStoreDatabase): CategoriesCacheDataSource =
        CategoriesCacheDataSource.Base(database.categoriesDao())
}