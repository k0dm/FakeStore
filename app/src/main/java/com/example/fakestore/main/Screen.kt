package com.example.fakestore.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(id: Int, supportFragmentManager: FragmentManager)


    object Empty : Screen {
        override fun show(id: Int, supportFragmentManager: FragmentManager) = Unit
    }

    abstract class Replace(private val clasz: Class<out Fragment>) : Screen {
        override fun show(id: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager
                .beginTransaction()
                .replace(id, clasz, null)
                .commit()
        }
    }

    abstract class Add(private val clasz: Class<out Fragment>) : Screen {
        override fun show(id: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(id, clasz, null)
                .addToBackStack(clasz.simpleName)
                .commit()
        }
    }

    object Pop : Screen {
        override fun show(id: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager.popBackStack()
        }
    }

}