package com.example.fakestore.favorites.presentation.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ViewholderFavoriteProductsBinding
import com.example.fakestore.databinding.ViewholderNoFavoritesBinding

class ProductsFavoritesAdapter(
    private val viewModel: ProductFavoriteActions,
    private val types: List<ProductFavoriteTypeUi> = listOf(
        ProductFavoriteTypeUi.Base, ProductFavoriteTypeUi.NoFavorites
    )
) : RecyclerView.Adapter<ProductFavoriteViewHolder>() {

    private val products = mutableListOf<ProductFavoriteUi>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newListProducts: List<ProductFavoriteUi>) {
        products.clear()
        products.addAll(newListProducts)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = types.indexOf(products[position].type())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductFavoriteViewHolder {
        return types[viewType].createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ProductFavoriteViewHolder, position: Int) {
        holder.bind(products[position], viewModel, this)
    }

    override fun getItemCount(): Int = products.size

    fun notify(productFavoriteUi: ProductFavoriteUi) {
        val index = products.indexOf(products.find { it.isTheSameById(productFavoriteUi.id()) }!!)
        products[index] = productFavoriteUi
        notifyItemChanged(index)
    }

    fun removeItem(productFavoriteUi: ProductFavoriteUi) {
        products.remove(productFavoriteUi)
        products.ifEmpty {
            products.add(ProductFavoriteUi.NoFavorites)
        }
    }

}

abstract class ProductFavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(
        productFavoriteUi: ProductFavoriteUi,
        viewModel: ProductFavoriteActions,
        adapter: ProductsFavoritesAdapter
    ) = Unit

    class Base(private val binding: ViewholderFavoriteProductsBinding) :
        ProductFavoriteViewHolder(binding.root) {

        override fun bind(
            productFavoriteUi: ProductFavoriteUi,
            viewModel: ProductFavoriteActions,
            adapter: ProductsFavoritesAdapter
        ) {
            binding.okay.setOnClickListener {
                productFavoriteUi.goToProductsDetails(viewModel)
            }

            productFavoriteUi.showFavoriteProducts(binding, adapter)
            binding.addToCartButton.setOnClickListener {
                productFavoriteUi.changeAddedToCart(viewModel)
                adapter.notifyItemChanged(adapterPosition)
            }

            binding.addToFavoriteButton.setOnClickListener {
                productFavoriteUi.changeFavorite(viewModel)
                adapter.removeItem(productFavoriteUi)

                adapter.notifyItemRemoved(adapterPosition)
            }
        }
    }

    class NoFavorites(private val binding: ViewholderNoFavoritesBinding) :
        ProductFavoriteViewHolder(binding.root) {

        override fun bind(
            productFavoriteUi: ProductFavoriteUi,
            viewModel: ProductFavoriteActions,
            adapter: ProductsFavoritesAdapter
        ) {
            productFavoriteUi.showNoFavorites(binding)
        }
    }
}

interface ProductFavoriteActions {

    fun goToProductsDetails(id: Int)

    fun changeAddedToCart(id: Int)

    fun changeAddedToFavorites(id: Int)
}