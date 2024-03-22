package com.example.fakestore.content.products.data.cloud

interface ProductResponseMapper<T> {

    fun map(
        id: Int,
        title: String,
        price: Double,
        description: String,
        category: String,
        imageUrl: String,
        rate: Double,
        count: Int,
    ): T
}

