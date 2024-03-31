package com.example.fakestore.favorites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fakestore.databinding.ViewholderFavoriteProductsBinding
import com.example.fakestore.databinding.ViewholderNoFavoritesBinding

interface ProductFavoriteTypeUi {

    fun createViewHolder(parent: ViewGroup): ProductFavoriteViewHolder

    object Base : ProductFavoriteTypeUi {
        override fun createViewHolder(parent: ViewGroup): ProductFavoriteViewHolder =
            ProductFavoriteViewHolder.Base(
                ViewholderFavoriteProductsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object NoFavorites : ProductFavoriteTypeUi {
        override fun createViewHolder(parent: ViewGroup): ProductFavoriteViewHolder =
            ProductFavoriteViewHolder.NoFavorites(
                ViewholderNoFavoritesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}