package com.example.fakestore.core

import com.example.fakestore.core.presentation.RunAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

@Suppress("UNCHECKED_CAST")
internal class FakeRunAsync : RunAsync {

    private var result: Any = Any()
    private var cacheUiBlock: (Any) -> Unit = {}

    override fun <T : Any> start(
        backgroundBlock: suspend () -> T,
        uiBlock: (T) -> Unit,
        coroutineScope: CoroutineScope
    ) = runBlocking {

        cacheUiBlock = uiBlock as (Any) -> Unit
        result = backgroundBlock.invoke()
    }

    fun pingResult() {
        cacheUiBlock.invoke(result)
    }
}