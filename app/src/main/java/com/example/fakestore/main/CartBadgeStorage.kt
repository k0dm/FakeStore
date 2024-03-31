package com.example.fakestore.main

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface CartBadgeStorage {

    interface Read {
        fun read(): Int
    }

    interface Save {
        fun save(number: Int)
    }


    interface Mutable : Read, Save

    class Base @Inject constructor(@ApplicationContext context: Context) : Mutable {

        private val storage = context.getSharedPreferences(
            "cartBadgeStorage",
            Context.MODE_PRIVATE
        )

        override fun read(): Int = storage.getInt(key, 0)

        override fun save(number: Int) = storage.edit().putInt(key, number).apply()

        companion object {
            private const val key = "key"
        }
    }
}