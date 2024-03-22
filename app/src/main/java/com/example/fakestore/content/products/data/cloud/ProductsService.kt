package com.example.fakestore.content.products.data.cloud

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    @GET("/products/category/{productsCategory}")
    fun loadProducts(@Path("productsCategory") category: String): Call<List<ProductResponse>>
}