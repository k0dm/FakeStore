package com.example.fakestore.di

import com.example.fakestore.products.categories.data.cloud.CategoriesCloudDataSource
import com.example.fakestore.products.categories.data.cloud.CategoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
        .build()

    @Provides
    fun provideCategoriesCloudDataSource(retrofit: Retrofit): CategoriesCloudDataSource =
        CategoriesCloudDataSource.Base(retrofit.create(CategoryService::class.java))


}