package com.example.fakestore.content.categories.presentation.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ViewholderCategoryBinding
import com.example.fakestore.databinding.ViewholderErrorBinding
import com.example.fakestore.databinding.ViewholderProgressBinding

class CategoriesAdapter(
    private val viewModel: CategoryAndRetryClickActions,
    private val types: List<CategoryTypeUi> = listOf(
        CategoryTypeUi.Base, CategoryTypeUi.Error, CategoryTypeUi.Progress
    )
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private val categories = mutableListOf<CategoryUi>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<CategoryUi>) {
        categories.clear()
        categories.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = types.indexOf(categories[position].type())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return types[viewType].createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], viewModel)
    }

    override fun getItemCount(): Int = categories.size
}

abstract class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(categoryUi: CategoryUi, viewModel: CategoryAndRetryClickActions) = Unit

    class Progress(binding: ViewholderProgressBinding) : CategoryViewHolder(binding.root)

    class Error(private val binding: ViewholderErrorBinding) : CategoryViewHolder(binding.root) {

        override fun bind(categoryUi: CategoryUi, viewModel: CategoryAndRetryClickActions) {
            binding.retryButton.setOnClickListener {
                viewModel.retry()
            }
            categoryUi.showMessage(binding)
        }
    }

    class Base(private val binding: ViewholderCategoryBinding) : CategoryViewHolder(binding.root) {

        override fun bind(categoryUi: CategoryUi, viewModel: CategoryAndRetryClickActions) {
            binding.categoryTextView.setOnClickListener {
                categoryUi.goToProducts(viewModel)
            }

            binding

            categoryUi.showCategory(binding)
        }
    }
}

interface CategoryAndRetryClickActions {

    fun retry()

    fun goToProducts(category: String)
}