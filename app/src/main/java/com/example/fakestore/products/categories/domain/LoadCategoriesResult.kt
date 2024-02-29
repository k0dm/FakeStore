package com.example.fakestore.products.categories.domain

interface LoadCategoriesResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(categories: List<String>)

        fun mapError(message: String)
    }

    data class Success(private val categories: List<String>) : LoadCategoriesResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(categories)
        }
    }

    data class Error(private val message: String) : LoadCategoriesResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }
}