package com.example.fakestore.content.products.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fakestore.content.products.presentation.adapter.ProductsAdapter
import com.example.fakestore.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    companion object {
        private const val KEY_CATEGORY = "key_category"

        fun newInstance(category: String) = ProductsFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_CATEGORY, category)
            }
        }
    }

    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding
        get() = _binding!!
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductsAdapter(viewModel = viewModel)

        category = requireArguments().getString(KEY_CATEGORY)!!
        binding.productsRecyclerView.adapter = adapter
        binding.categoryTextView.text = category
        binding.backToCategoriesImageButton.setOnClickListener {
            viewModel.goToCategories()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(adapter = adapter)
        }

        viewModel.productPositionLiveData().observe(viewLifecycleOwner) {
            adapter.notifyById(it)
        }

        viewModel.init(category)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}