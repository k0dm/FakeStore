package com.example.fakestore.content.products.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fakestore.databinding.ViewholderErrorBinding
import com.example.fakestore.databinding.ViewholderProductsBinding
import com.example.fakestore.databinding.ViewholderProgressBinding

interface ProductTypeUi {

    fun createViewHolder(parent: ViewGroup): ProductViewHolder

    object Base : ProductTypeUi {

        override fun createViewHolder(parent: ViewGroup) = ProductViewHolder.Base(
            ViewholderProductsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object Progress : ProductTypeUi {

        override fun createViewHolder(parent: ViewGroup) = ProductViewHolder.Progress(
            ViewholderProgressBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object Error : ProductTypeUi {

        override fun createViewHolder(parent: ViewGroup) = ProductViewHolder.Error(
            ViewholderErrorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object Empty : ProductTypeUi {
        override fun createViewHolder(parent: ViewGroup): ProductViewHolder {
            TODO("Not yet implemented")
        }

    }
}