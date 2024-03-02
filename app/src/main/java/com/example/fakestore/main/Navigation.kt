package com.example.fakestore.main

import com.example.fakestore.core.LiveDataWrapper
import com.example.fakestore.core.ProvideLiveData
import com.example.fakestore.core.UiUpdate

interface Navigation {

    interface Navigate : UiUpdate<Screen>

    interface Read : ProvideLiveData<Screen>

    interface Mutable : Navigate, Read

    class Base() : Mutable, LiveDataWrapper.Single<Screen>()
}