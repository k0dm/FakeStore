package com.example.fakestore.cart.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fakestore.databinding.ViewholderCartProductBinding
import com.example.fakestore.databinding.ViewholderNoCartBinding

interface ProductCartTypeUi {

    fun createViewHolder(parent: ViewGroup): ProductCartViewHolder

    object Base : ProductCartTypeUi {
        override fun createViewHolder(parent: ViewGroup): ProductCartViewHolder =
            ProductCartViewHolder.Base(
                ViewholderCartProductBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object NoCarts : ProductCartTypeUi {
        override fun createViewHolder(parent: ViewGroup): ProductCartViewHolder =
            ProductCartViewHolder.NoCarts(
                ViewholderNoCartBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}