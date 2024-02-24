package com.example.fakestore.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.fakestore.R
import com.example.fakestore.core.ProvideViewModel
import com.example.fakestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = viewModel(MainViewModel::class.java)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemProducts -> {
                    viewModel.navigateToProduct()
                    true
                }

                R.id.itemFavorites -> {
                    true
                }

                R.id.itemCart -> {
                    true
                }

                R.id.itemProfile -> {
                    true
                }

                else -> false
            }
        }

    }

    override fun <T : ViewModel> viewModel(clazz: Class<out T>): T {
        TODO("ProvideViewModel")
    }
}
