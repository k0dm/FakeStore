package com.example.fakestore.products.categories.data

import java.net.UnknownHostException

interface HandleError {

    fun handle(exception: Exception): String

    class Base : HandleError {
        override fun handle(exception: Exception): String {
            return if (exception is UnknownHostException) {
                "No internet connection"
            } else {
                "Service Unavailable"
            }
        }
    }
}