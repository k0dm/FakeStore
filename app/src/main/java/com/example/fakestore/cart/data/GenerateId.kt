package com.example.fakestore.cart.data

import java.util.UUID
import javax.inject.Inject

interface GenerateId {

    fun random(): String

    class Base @Inject constructor() : GenerateId {
        override fun random() = UUID.randomUUID().toString()
    }
}