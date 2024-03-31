package com.example.fakestore.content.products.data.cloud

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("title")
    private val title: String,
    @SerializedName("price")
    private val price: Double,
    @SerializedName("description")
    private val description: String,
    @SerializedName("category")
    private val category: String,
    @SerializedName("image")
    private val imageUrl: String,
    @SerializedName("rating")
    private val rating: ProductRating
) {
    fun <T : Any> map(mapper: ProductResponseMapper<T>): T = mapper.map(
        id, title, price, description, category, imageUrl, rating.rate, rating.count
    )
}