package com.example.fakestore.cart.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fakestore.cart.presentation.adapter.ProductsCartAdapter
import com.example.fakestore.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductsCartAdapter(viewModel)
        binding.productsRecyclerView.adapter = adapter

        binding.proceedToPaymentButton.setOnClickListener {
            viewModel.proceedToPayment()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(adapter)
        }
        viewModel.subtotalLiveData().observe(viewLifecycleOwner) {
            it.show(binding)
        }

        viewModel.productPositionLiveData().observe(viewLifecycleOwner) {
            viewModel.init()
        }

        viewModel.init()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}