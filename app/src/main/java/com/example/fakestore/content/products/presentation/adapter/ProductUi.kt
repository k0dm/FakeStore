package com.example.fakestore.content.products.presentation.adapter

import com.example.fakestore.R
import com.example.fakestore.databinding.ViewholderErrorBinding
import com.example.fakestore.databinding.ViewholderProductsBinding
import com.squareup.picasso.Picasso

interface ProductUi {

    fun id(): Int = -1

    fun type(): ProductTypeUi

    fun showMessage(binding: ViewholderErrorBinding) = Unit

    fun showProducts(binding: ViewholderProductsBinding, adapter: ProductsAdapter) = Unit

    fun goToProductsDetails(viewModel: ProductAndRetryClickActions) = Unit

    fun retry(viewModel: ProductAndRetryClickActions) = Unit

    fun changeAddedToCart(viewModel: ProductAndRetryClickActions) = Unit

    fun changeFavorite(viewModel: ProductAndRetryClickActions) = Unit

    fun isTheSameById(id: Int): Boolean = false

    data class Base(
        private val id: Int,
        private var title: String,
        private val price: Double,
        private val description: String,
        private val category: String,
        private val imageUrl: String,
        private val rate: Double,
        private val count: Int,
        private var favorite: Boolean,
        private var addedToCart: Boolean
    ) : ProductUi {

        override fun id() = id

        override fun retry(viewModel: ProductAndRetryClickActions) {
            viewModel.retry(category)
        }

        override fun type() = ProductTypeUi.Base

        override fun changeFavorite(viewModel: ProductAndRetryClickActions) {
            favorite = !favorite
            viewModel.changeAddedToFavorites(id)
        }

        override fun goToProductsDetails(viewModel: ProductAndRetryClickActions) {
            viewModel.goToProductsDetails(id = id, category = category)
        }

        override fun changeAddedToCart(viewModel: ProductAndRetryClickActions) {
            addedToCart = !addedToCart
            viewModel.changeAddedToCart(id)
        }

        override fun showProducts(binding: ViewholderProductsBinding, adapter: ProductsAdapter) {
            binding.priceTextView.text = price.toString()
            binding.rateTextView.text = rate.toString()
            binding.titleTextView.text = title
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.products)
                .into(binding.imageProductImageView);
            binding.addToFavoriteButton.setImageResource(
                if (favorite) R.drawable.favorite else R.drawable.favorite_empty
            )

            binding.addToCartButton.setImageResource(
                if (addedToCart) R.drawable.shopping_bag_full else R.drawable.shopping_bag_icon_empty
            )

            binding.productViewHolder.setOnClickListener {

            }

            binding.firstStarImageView.setImageResource(R.drawable.filled_star)
            binding.secondStarImageView.setImageResource(
                if (rate >= 2) {
                    R.drawable.filled_star
                } else if (rate >= 1.5) {
                    R.drawable.star_half_filled
                } else {
                    R.drawable.empty_star
                }
            )
            binding.thirdStarImageView.setImageResource(
                if (rate >= 3) {
                    R.drawable.filled_star
                } else if (rate >= 2.5) {
                    R.drawable.star_half_filled
                } else {
                    R.drawable.empty_star
                }
            )
            binding.fourStarImageView.setImageResource(
                if (rate >= 4) {
                    R.drawable.filled_star
                } else if (rate >= 3.5) {
                    R.drawable.star_half_filled
                } else {
                    R.drawable.empty_star
                }
            )
            binding.fifthStarImageView.setImageResource(
                if (rate >= 5) {
                    R.drawable.filled_star
                } else if (rate >= 4.5) {
                    R.drawable.star_half_filled
                } else {
                    R.drawable.empty_star
                }
            )

        }

        override fun isTheSameById(id: Int) = this.id == id
    }

    object Progress : ProductUi {

        override fun type() = ProductTypeUi.Progress
    }

    data class Error(private val message: String) : ProductUi {

        override fun type() = ProductTypeUi.Error

        override fun showMessage(binding: ViewholderErrorBinding) {
            binding.errorTextView.text = message
        }
    }

}