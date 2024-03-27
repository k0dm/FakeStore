package com.example.fakestore.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fakestore.databinding.FragmentFavoritesBinding
import com.example.fakestore.favorites.presentation.adapter.ProductsFavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductsFavoritesAdapter(viewModel)
        binding.productsRecyclerView.adapter = adapter
        binding.productsRecyclerView.itemAnimator = null
        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(adapter)
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