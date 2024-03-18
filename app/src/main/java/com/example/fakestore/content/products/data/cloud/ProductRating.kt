package com.example.fakestore.content.products.data.cloud

import com.google.gson.annotations.SerializedName

data class ProductRating(
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("count")
    val count: Int
)