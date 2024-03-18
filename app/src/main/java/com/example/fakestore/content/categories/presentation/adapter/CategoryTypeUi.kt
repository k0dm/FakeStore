package com.example.fakestore.content.categories.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fakestore.databinding.ViewholderCategoryBinding
import com.example.fakestore.databinding.ViewholderErrorBinding
import com.example.fakestore.databinding.ViewholderProgressBinding

interface CategoryTypeUi {

    fun createViewHolder(parent: ViewGroup): CategoryViewHolder

    object Base : CategoryTypeUi {

        override fun createViewHolder(parent: ViewGroup) = CategoryViewHolder.Base(
            ViewholderCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object Progress : CategoryTypeUi {

        override fun createViewHolder(parent: ViewGroup) = CategoryViewHolder.Progress(
            ViewholderProgressBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    object Error : CategoryTypeUi {

        override fun createViewHolder(parent: ViewGroup) = CategoryViewHolder.Error(
            ViewholderErrorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}