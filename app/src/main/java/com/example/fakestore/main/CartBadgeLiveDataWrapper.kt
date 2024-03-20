package com.example.fakestore.main

import com.example.fakestore.core.UiUpdate
import com.example.fakestore.core.presentation.LiveDataWrapper
import com.example.fakestore.core.presentation.ProvideLiveData
import javax.inject.Inject

interface CartBadgeLiveDataWrapper {

    interface Update : UiUpdate<Int>

    interface Provide : ProvideLiveData<Int>

    interface Mutable : Update, Provide

    class Base @Inject constructor() : Mutable, LiveDataWrapper.Single<Int>()
}