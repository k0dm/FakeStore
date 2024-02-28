package com.example.fakestore.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync {

    fun <T : Any> start(
        backgroundBlock: suspend () -> T,
        uiBlock: (T) -> Unit,
        coroutineScope: CoroutineScope
    )

    class Base() : RunAsync {
        override fun <T : Any> start(
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit,
            coroutineScope: CoroutineScope
        ) {
            coroutineScope.launch(Dispatchers.IO) {
                val result = backgroundBlock.invoke()
                withContext(Dispatchers.Main) {
                    uiBlock.invoke(result)
                }
            }
        }
    }
}