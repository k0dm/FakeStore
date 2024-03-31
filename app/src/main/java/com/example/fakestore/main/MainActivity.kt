package com.example.fakestore.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fakestore.R
import com.example.fakestore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemProducts -> {
                    viewModel.navigateToProducts()
                    true
                }

                R.id.itemFavorites -> {
                    viewModel.navigateToFavorites()
                    true
                }

                R.id.itemCart -> {
                    viewModel.navigateToCart()
                    true
                }

                R.id.itemProfile -> {
                    true
                }

                else -> false
            }
        }

        val cartBadge = binding.bottomNavigation.getOrCreateBadge(R.id.itemCart)

        viewModel.cartBadgeLiveData().observe(this) {
            if (it == 0) {
                cartBadge.isVisible = false
            } else {
                cartBadge.number = it

                cartBadge.isVisible = true
            }
        }

        viewModel.navigationLiveData().observe(this) {
            it.show(binding.container.id, supportFragmentManager)
        }

        viewModel.init(isFirstRun = savedInstanceState == null)
    }
}

