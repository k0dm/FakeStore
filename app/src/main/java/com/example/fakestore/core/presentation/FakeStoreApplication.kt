package com.example.fakestore.core

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FakeStoreApplication : Application()
//{
//
//    private lateinit var viewModelFactory: ProvideViewModel
//
//    override fun onCreate() {
//        super.onCreate()
//        viewModelFactory = ProvideViewModel.Factory(Core.Base(this))
//    }
//
//    override fun <T : ViewModel> viewModel(clazz: Class<out T>): T {
//        return viewModelFactory.viewModel(clazz)
//    }
//}