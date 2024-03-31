package com.example.fakestore.content.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fakestore.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    companion object {
        private const val KEY_ID = "key_id"

        fun newInstance(id: Int) = ProductDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_ID, id)
            }
        }
    }


    private var _binding: FragmentProductDetailsBinding? = null
    private val binding: FragmentProductDetailsBinding
        get() = _binding!!
    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getInt(KEY_ID)

        binding.backToProductsImageButton.setOnClickListener {
            viewModel.goToProducts(id)
        }

        binding.addedToFavoriteButton.setOnClickListener {
            viewModel.changeFavorite(id)
        }

        binding.actionButton.setOnClickListener {
            viewModel.changeAddedToCart(id)
        }

        viewModel.navigationLiveData().observe(viewLifecycleOwner) {
            it.show(binding)
        }

        viewModel.init(id)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}