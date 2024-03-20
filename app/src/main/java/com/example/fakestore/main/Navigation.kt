package com.example.fakestore.main

import com.example.fakestore.core.UiUpdate
import com.example.fakestore.core.presentation.LiveDataWrapper
import com.example.fakestore.core.presentation.ProvideLiveData
import javax.inject.Inject
import javax.inject.Singleton

interface Navigation {

    interface Navigate : UiUpdate<Screen>

    interface Read : ProvideLiveData<Screen>

    interface Mutable : Navigate, Read

    @Singleton
    class Base @Inject constructor(): Mutable, LiveDataWrapper.Single<Screen>()
}