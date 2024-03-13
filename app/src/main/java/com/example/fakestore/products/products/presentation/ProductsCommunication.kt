package com.example.fakestore.products.products.presentation

import androidx.lifecycle.LiveData

interface ProductsCommunication {

    fun updateUi(value: ProductsUiState)

    fun liveData(): LiveData<ProductsUiState>
}