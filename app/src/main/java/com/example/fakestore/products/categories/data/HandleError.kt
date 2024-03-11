package com.example.fakestore.products.categories.data

import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

interface HandleError {

    fun handle(exception: Exception): String

    @Singleton
    class Base @Inject constructor() : HandleError {

        override fun handle(exception: Exception): String {
            return if (exception is UnknownHostException) {
                "No internet connection"
            } else {
                "Service Unavailable"
            }
        }
    }
}