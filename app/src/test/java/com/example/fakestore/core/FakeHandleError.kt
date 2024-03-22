package com.example.fakestore.core

import com.example.fakestore.content.categories.data.HandleError

internal class FakeHandleError() : HandleError {

    override fun handle(exception: Exception): String {
        return "Problems"
    }
}