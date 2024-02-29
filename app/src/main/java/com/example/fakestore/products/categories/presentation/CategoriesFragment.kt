package com.example.fakestore.products.categories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fakestore.core.ProvideViewModel
import com.example.fakestore.databinding.FragmentCategoriesBinding
import com.example.fakestore.products.categories.presentation.adapter.CategoriesAdapter

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (activity as ProvideViewModel).viewModel(CategoriesViewModel::class.java)
        val adapter = CategoriesAdapter(viewModel)
        binding.categoriesRecyclerView.adapter = adapter

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(adapter)
        }

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

