package com.example.fakestore.cart.presentation.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.cart.presentation.ProductCartUi
import com.example.fakestore.databinding.ViewholderCartProductBinding
import com.example.fakestore.databinding.ViewholderNoCartBinding

class ProductsCartAdapter(
    private val viewModel: ProductCartActions,
    private val types: List<ProductCartTypeUi> = listOf(
        ProductCartTypeUi.Base, ProductCartTypeUi.NoCarts
    )
) : RecyclerView.Adapter<ProductCartViewHolder>() {

    private val products = mutableListOf<ProductCartUi>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newListProducts: List<ProductCartUi>) {
        products.clear()
        products.addAll(newListProducts)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = types.indexOf(products[position].type())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCartViewHolder {
        return types[viewType].createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ProductCartViewHolder, position: Int) {
        holder.bind(products[position], viewModel, this)
    }

    override fun getItemCount(): Int = products.size

    fun notify(productCartUi: ProductCartUi) {
        val index = products.indexOf(products.find { it.isTheSameById(productCartUi.id()) }!!)
        products[index] = productCartUi
        notifyItemChanged(index)
    }

    fun removeItem(productCartUi: ProductCartUi) {
        products.remove(productCartUi)
        products.ifEmpty {
            products.add(ProductCartUi.NoCarts)
        }
    }

}

abstract class ProductCartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(
        productCartUi: ProductCartUi,
        viewModel: ProductCartActions,
        adapter: ProductsCartAdapter
    ) = Unit

    class Base(private val binding: ViewholderCartProductBinding) :
        ProductCartViewHolder(binding.root) {

        override fun bind(
            productCartUi: ProductCartUi,
            viewModel: ProductCartActions,
            adapter: ProductsCartAdapter
        ) {
            productCartUi.showCartsProducts(binding, adapter)

            binding.cartProductViewHolder.setOnClickListener {
                productCartUi.goToProductsDetails(viewModel)
            }
            binding.removeCartProductButton.setOnClickListener {
                productCartUi.removeCartProduct(viewModel)
                adapter.removeItem(productCartUi)

                adapter.notifyItemRemoved(adapterPosition)
            }

            binding.addQuantityProductButton.setOnClickListener {
                productCartUi.changeAddQuantity(viewModel)
            }
            binding.removeQuantityProductButton.setOnClickListener {
                productCartUi.changeRemoveQuantity(viewModel)
            }
        }
    }

    class NoCarts(private val binding: ViewholderNoCartBinding) :
        ProductCartViewHolder(binding.root) {

        override fun bind(
            productCartUi: ProductCartUi,
            viewModel: ProductCartActions,
            adapter: ProductsCartAdapter
        ) {
            productCartUi.showNoCarts(binding)
        }
    }
}

interface ProductCartActions {

    fun proceedToPayment()

    fun goToDetails(id: Int)

    fun removeFromCart(id: Int)

    fun changeQuantity(productId: Int, quantity: Int)
}