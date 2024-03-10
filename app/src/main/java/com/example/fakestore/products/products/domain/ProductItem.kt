package com.example.fakestore.products.products.domain

interface ProductItem {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun map(
            id: Int,
            title: String,
            price: Double,
            description: String,
            category: String,
            imageUrl: String,
            rate: Double,
            count: Int,
            favorite: Boolean,
            addedToCart: Boolean
        ): T
    }

    data class Base(
        private val id: Int,
        private val title: String,
        private val price: Double,
        private val description: String,
        private val category: String,
        private val imageUrl: String,
        private val rate: Double,
        private val count: Int,
        private val favorite: Boolean,
        private val addedToCart: Boolean
    ) : ProductItem {

        override fun <T : Any> map(mapper: Mapper<T>): T = mapper.map(
            id, title, price, description, category, imageUrl, rate, count, favorite, addedToCart
        )
    }
}