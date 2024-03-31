package com.example.fakestore.cart.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

interface LocalDate {

    fun now(): String

    class Base @Inject constructor() : LocalDate {

        private val localDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("uk", "UA"))

        override fun now(): String = localDateFormat.format(Date())
    }
}