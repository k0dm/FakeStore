package com.example.fakestore.core

import com.example.fakestore.products.categories.data.HandleError

internal class FakeHandleError() : HandleError {

    override fun handle(exception: Exception): String {
        return "Problems"
    }
}