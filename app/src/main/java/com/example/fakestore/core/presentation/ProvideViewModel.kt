package com.example.fakestore.core

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.fakestore.core.data.FakeStoreDatabase
import com.example.fakestore.main.MainViewModel
import com.example.fakestore.main.Navigation
import com.example.fakestore.products.categories.data.BaseCategoriesRepository
import com.example.fakestore.products.categories.data.HandleError
import com.example.fakestore.products.categories.data.cache.CategoriesCacheDataSource
import com.example.fakestore.products.categories.data.cloud.CategoriesCloudDataSource
import com.example.fakestore.products.categories.data.cloud.CategoryService
import com.example.fakestore.products.categories.presentation.CategoriesCommunication
import com.example.fakestore.products.categories.presentation.CategoriesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(clazz: Class<out T>): T


    class Factory(private val core: Core) : ProvideViewModel {

        private val viewModelStore = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(clazz: Class<out T>): T {
            return if (viewModelStore.containsKey(clazz)) {
                viewModelStore[clazz]
            } else {
                val viewModel = when (clazz) {
                    MainViewModel::class.java -> MainViewModel(navigation = core.navigation())
                    CategoriesViewModel::class.java -> CategoriesViewModel(
                        navigation = core.navigation(),
                        communication = CategoriesCommunication.Base(),
                        repository = BaseCategoriesRepository(
                            cloudDataSource = CategoriesCloudDataSource.Base(
                                core.retrofit().create(CategoryService::class.java)
                            ),
                            cacheDataSource = CategoriesCacheDataSource.Base(
                                core.database().categoriesDao()
                            ),
                            handleError = HandleError.Base()
                        ),
                        runAsync = core.runAsync()
                    )

                    else -> throw IllegalAccessException("No such ViewModel with class $clazz")
                }
                viewModelStore.put(clazz, viewModel)
                viewModel
            } as T
        }
    }
}

interface Core {

    fun navigation(): Navigation.Mutable

    fun runAsync(): RunAsync

    fun database(): FakeStoreDatabase

    fun retrofit(): Retrofit

    class Base(context: Context) : Core {

        private val navigation: Navigation.Mutable = Navigation.Base()
        private val database: FakeStoreDatabase = Room
            .databaseBuilder(
                context,
                FakeStoreDatabase::class.java,
                "fake_store_db"
            ).build()
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
            .build()
        private val runAsync = RunAsync.Base()

        override fun navigation() = navigation

        override fun runAsync(): RunAsync = runAsync

        override fun database() = database

        override fun retrofit() = retrofit
    }
}