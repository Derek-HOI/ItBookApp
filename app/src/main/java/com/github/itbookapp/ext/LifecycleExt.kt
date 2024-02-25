package com.github.itbookapp.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleOwner.SetEvents(
    listener: (Lifecycle.Event) -> Unit
) {
    DisposableEffect(key1 = this, effect = {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            listener(event)
        }
        // Add the observer to the lifecycle
        lifecycle.addObserver(observer)
        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycle.removeObserver(observer)
        }
    })
}
