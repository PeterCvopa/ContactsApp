package com.cvopa.peter.play.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> composeViewModel(crossinline block: () -> VM): VM = viewModel(
    modelClass = VM::class.java,
    factory = object : ViewModelProvider.Factory {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
            @Suppress("UNCHECKED_CAST")
            return block() as VM
        }
    },
).apply {
    (this as? LifecycleObserver)?.hookOnLifecycle()
}

@Composable
fun LifecycleObserver.hookOnLifecycle() {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this@hookOnLifecycle)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(this@hookOnLifecycle)
        }
    }
}