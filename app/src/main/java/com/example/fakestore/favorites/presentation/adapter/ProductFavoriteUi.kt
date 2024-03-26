package com.example.fakestore.favorites.presentation.adapter

import com.example.fakestore.R
import com.example.fakestore.databinding.ViewholderFavoriteProductsBinding
import com.example.fakestore.databinding.ViewholderNoFavoritesBinding
import com.squareup.picasso.Picasso

interface ProductFavoriteUi {

    fun id(): Int = -1

    fun changeAddedToCart(viewModel: ProductFavoriteActions) = Unit

    fun changeFavorite(viewModel: ProductFavoriteActions) = Unit

    fun goToProductsDetails(viewModel: ProductFavoriteActions) = Unit

    fun isTheSameById(id: Int): Boolean = false

    fun type(): ProductFavoriteTypeUi

    fun showFavoriteProducts(
        binding: ViewholderFavoriteProductsBinding,
        adapter: ProductsFavoritesAdapter
    ) = Unit

    fun showNoFavorites(binding: ViewholderNoFavoritesBinding) = Unit

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
    ) : ProductFavoriteUi {

        override fun type(): ProductFavoriteTypeUi = ProductFavoriteTypeUi.Base

        override fun showFavoriteProducts(
            binding: ViewholderFavoriteProductsBinding,
            adapter: ProductsFavoritesAdapter
        ) {
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

        override fun changeFavorite(viewModel: ProductFavoriteActions) {
            favorite = !favorite
            viewModel.changeAddedToFavorites(id)
        }

        override fun goToProductsDetails(viewModel: ProductFavoriteActions) {
            viewModel.goToProductsDetails(id = id)
        }

        override fun changeAddedToCart(viewModel: ProductFavoriteActions) {
            addedToCart = !addedToCart
            viewModel.changeAddedToCart(id)
        }
    }

    object NoFavorites : ProductFavoriteUi {

        override fun type() = ProductFavoriteTypeUi.NoFavorites

        override fun showNoFavorites(binding: ViewholderNoFavoritesBinding) {
            binding.textView.setText(R.string.add_products_to_your_favorites) // Add products to your favorites
        }
    }

}