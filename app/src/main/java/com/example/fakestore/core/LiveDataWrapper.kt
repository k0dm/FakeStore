package com.example.fakestore.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper<T : Any> : UiUpdate<T>, ProvideLiveData<T> {

    abstract class Single<T : Any>(
        private val liveData: MutableLiveData<T> = SingleLiveEvent()
    ) : LiveDataWrapper<T> {

        override fun updateUi(value: T) {
            liveData.value = value
        }

        override fun liveData(): LiveData<T> = liveData
    }
}