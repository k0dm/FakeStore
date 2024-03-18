package com.example.fakestore.main

import com.example.fakestore.core.presentation.LiveDataWrapper
import javax.inject.Inject

interface CartBadgeLiveDataWrapper : LiveDataWrapper<Int> {

    class Base @Inject constructor() : CartBadgeLiveDataWrapper, LiveDataWrapper.Single<Int>()
}