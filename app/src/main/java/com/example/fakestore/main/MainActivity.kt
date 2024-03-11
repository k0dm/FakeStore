package com.example.fakestore.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fakestore.R
import com.example.fakestore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
        viewModel.init(isFirstRun = savedInstanceState == null)

        viewModel.liveData().observe(this) {
            it.show(binding.container.id, supportFragmentManager)
        }
    }
}
