package com.example.fakestore.cart.presentation

import android.view.View
import com.example.fakestore.cart.domain.CartRepository
import com.example.fakestore.cart.presentation.adapter.ProductCartActions
import com.example.fakestore.content.details.presentation.ProductDetailsScreen
import com.example.fakestore.content.products.presentation.ProductPositionLiveDataWrapper
import com.example.fakestore.core.BaseViewModel
import com.example.fakestore.core.presentation.LiveDataWrapper
import com.example.fakestore.core.presentation.RunAsync
import com.example.fakestore.databinding.FragmentCartBinding
import com.example.fakestore.main.CartBadgeLiveDataWrapper
import com.example.fakestore.main.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val navigation: Navigation.Navigate,
    private val communication: CartCommunication,
    private val subtotalLiveDataWrapper: SubtotalLiveDataWrapper,
    private val productPositionLiveDataWrapper: ProductPositionLiveDataWrapper.Provide,
    private val badgeLiveDataWrapper: CartBadgeLiveDataWrapper.Update,
    private val repository: CartRepository,
    runAsync: RunAsync
) : BaseViewModel(runAsync), ProductCartActions {

    fun productPositionLiveData() = productPositionLiveDataWrapper.liveData()

    fun subtotalLiveData() = subtotalLiveDataWrapper.liveData()

    fun init() {
        runAsync({
            repository.cartProducts()
        }) { it ->
            communication.updateUi(
                if (it.isEmpty()) {
                    CartUiState.NoCarts
                } else {
                    CartUiState.Base(it.map {
                        ProductCartUi.Base(
                            it.id,
                            it.title,
                            it.price,
                            it.imageUrl,
                            it.quantity
                        )
                    })

                }
            )
            subtotal()
        }
    }

    fun liveData() = communication.liveData()

    override fun removeFromCart(id: Int) {
        runAsync({
            repository.removeFromCart(id)
        }) { value ->
            badgeLiveDataWrapper.updateUi(value)
            init()
        }
    }

    override fun goToDetails(id: Int) {
        navigation.updateUi(ProductDetailsScreen(id))
    }

    override fun changeQuantity(productId: Int, quantity: Int) {
        runAsync({
            repository.changeAmountProductFromCart(productId, quantity)
        }) {
            init()
        }
    }

    fun subtotal() {
        runAsync(
            { repository.subtotal() }
        ) { price ->
            subtotalLiveDataWrapper.updateUi(
                if (price == 0.0) {
                    SubtotalUiState.Hide
                } else SubtotalUiState.Visible(price)
            )
        }
    }

    override fun proceedToPayment() {
        runAsync({
            repository.proceedToPayment()
        }) {
            init()
            badgeLiveDataWrapper.updateUi(0)
        }
    }
}

interface SubtotalUiState {

    fun show(binding: FragmentCartBinding)

    object Hide : SubtotalUiState {
        override fun show(binding: FragmentCartBinding) {
            binding.subtotalLinearLayout.visibility = View.GONE
        }
    }

    data class Visible(private val price: Double) : SubtotalUiState {
        override fun show(binding: FragmentCartBinding) {
            binding.subtotalLinearLayout.visibility = View.VISIBLE
            binding.subtotalTextView.text = String.format(Locale.ENGLISH, "%.2f", price)
        }
    }
}

interface SubtotalLiveDataWrapper : LiveDataWrapper<SubtotalUiState> {

    class Base @Inject constructor() : SubtotalLiveDataWrapper,
        LiveDataWrapper.Single<SubtotalUiState>()
}