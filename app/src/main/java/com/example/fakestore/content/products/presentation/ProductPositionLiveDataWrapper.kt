package com.example.fakestore.content.products.presentation

import android.util.Log
import com.example.fakestore.core.UiUpdate
import com.example.fakestore.core.presentation.LiveDataWrapper
import com.example.fakestore.core.presentation.ProvideLiveData
import javax.inject.Inject

interface ProductPositionLiveDataWrapper {

    interface Update : UiUpdate<Int>

    interface Provide : ProvideLiveData<Int>

    interface Mutable : Update, Provide

    class Base @Inject constructor() : Mutable, LiveDataWrapper.Single<Int>() {
        init {
            Log.d("k0dm", "ProductPositionLiveDataWrapper ${hashCode()} ")
        }
    }
}