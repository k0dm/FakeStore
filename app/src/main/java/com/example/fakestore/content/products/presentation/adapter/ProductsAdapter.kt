package com.example.fakestore.content.products.presentation.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ViewholderErrorBinding
import com.example.fakestore.databinding.ViewholderProductsBinding
import com.example.fakestore.databinding.ViewholderProgressBinding

class ProductsAdapter(
    private val viewModel: ProductAndRetryClickActions,
    private val types: List<ProductTypeUi> = listOf(
        ProductTypeUi.Base, ProductTypeUi.Error, ProductTypeUi.Progress
    )
) : RecyclerView.Adapter<ProductViewHolder>() {

    private val products = mutableListOf<ProductUi>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newListProducts: List<ProductUi>) {
        products.clear()
        products.addAll(newListProducts)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = types.indexOf(products[position].type())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        types[viewType].createViewHolder(parent)


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], viewModel, this)
    }

    override fun getItemCount(): Int = products.size

    fun notify(productUi: ProductUi) {
        val index = products.indexOf(products.find { it.isTheSameById(productUi.id()) }!!)
        products[index] = productUi
        notifyItemChanged(index)
    }
}

abstract class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(
        productUi: ProductUi,
        viewModel: ProductAndRetryClickActions,
        adapter: ProductsAdapter
    ) = Unit

    class Progress(binding: ViewholderProgressBinding) : ProductViewHolder(binding.root)

    class Error(private val binding: ViewholderErrorBinding) : ProductViewHolder(binding.root) {

        override fun bind(
            productUi: ProductUi,
            viewModel: ProductAndRetryClickActions,
            adapter: ProductsAdapter
        ) {
            binding.retryButton.setOnClickListener {
                productUi.retry(viewModel)
            }
            productUi.showMessage(binding)
        }
    }

    class Base(private val binding: ViewholderProductsBinding) :
        ProductViewHolder(binding.root) {

        override fun bind(
            productUi: ProductUi,
            viewModel: ProductAndRetryClickActions,
            adapter: ProductsAdapter
        ) {
            binding.imageProductImageView.setOnClickListener {
                productUi.goToProductsDetails(viewModel)
            }

            productUi.showProducts(binding, adapter)
            binding.addToCartButton.setOnClickListener {
                productUi.changeAddedToCart(viewModel)
                adapter.notifyItemChanged(adapterPosition)
            }
            binding.addToFavoriteButton.setOnClickListener {
                productUi.changeFavorite(viewModel)
                adapter.notifyItemChanged(adapterPosition)
            }
        }
    }
}

interface ProductAndRetryClickActions {

    fun retry(category: String)

    fun goToProductsDetails(id: Int)

    fun changeAddedToCart(id: Int)

    fun changeAddedToFavorites(id: Int)

}