package com.example.fakestore.core

interface LiveDataWrapper<T : Any> : UiUpdate<T>, ProvideLiveData<T>