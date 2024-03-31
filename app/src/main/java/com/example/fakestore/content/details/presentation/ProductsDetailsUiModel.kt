package com.example.fakestore.content.details.presentation

import com.example.fakestore.R
import com.example.fakestore.databinding.FragmentProductDetailsBinding

import com.squareup.picasso.Picasso

data class ProductsDetailsUiModel(
    private val id: Int,
    private val title: String,
    private val price: Double,
    private val description: String,
    private val category: String,
    private val imageUrl: String,
    private val rate: Double,
    private val count: Int,
    private val favorite: Boolean,
    private val addedToCart: Boolean
) {
    fun show(binding: FragmentProductDetailsBinding) {

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.products)
            .into(binding.imageProductImageView);

        binding.addedToFavoriteButton.setImageResource(
            if (favorite == true) R.drawable.favorite else R.drawable.favorite_empty
        )

        binding.actionButton.setText(
            if (addedToCart == true) "Added To Card" else "Buy"
        )

        binding.titleTextView.text = title
        binding.priceTextView.text = price.toString()
        binding.rateTextView.text = rate.toString()
        binding.descriptionTextView.text = description

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
}