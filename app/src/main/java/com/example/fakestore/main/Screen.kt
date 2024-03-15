package com.example.fakestore.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(containerId: Int, supportFragmentManager: FragmentManager)

    object Empty : Screen {
        override fun show(containerId: Int, supportFragmentManager: FragmentManager) = Unit
    }

    abstract class Replace(private val clasz: Class<out Fragment>) : Screen {
        override fun show(containerId: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager
                .beginTransaction()
                .replace(containerId, clasz, null)
                .commit()
        }
    }

    abstract class Add(private val clasz: Class<out Fragment>) : Screen {
        override fun show(containerId: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(containerId, fragment())
                .addToBackStack(clasz.simpleName)
                .commit()
        }

        protected open fun fragment(): Fragment = clasz.getDeclaredConstructor().newInstance()
    }

    object Pop : Screen {
        override fun show(containerId: Int, supportFragmentManager: FragmentManager) {
            supportFragmentManager.popBackStack()
        }
    }

}