package com.example.fakestore.main

import com.example.fakestore.core.presentation.LiveDataWrapper
import javax.inject.Inject
import javax.inject.Singleton

interface CartBadgeLiveDataWrapper : LiveDataWrapper<Int> {

    @Singleton
    class Base @Inject constructor() : CartBadgeLiveDataWrapper, LiveDataWrapper.Single<Int>()
}