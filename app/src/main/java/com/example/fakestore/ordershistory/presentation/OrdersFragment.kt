package com.example.fakestore.ordershistory.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fakestore.databinding.FragmentOrdersBinding
import com.example.fakestore.ordershistory.presentation.adapter.orders.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ordersAdapter = OrderAdapter()
        binding.ordersRecyclerView.adapter = ordersAdapter

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(adapter = ordersAdapter)
        }
        viewModel.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}