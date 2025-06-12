package com.testapp.audiobookplayer.presentation.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.flow.Flow

/**
 * An extension to observe, collect and handle [Effect], presumably from [Store.observeEffect].
 */
@Composable
fun <Effect : UiEffect> ConsumeEffects(
    flow: Flow<Effect>,
    consumeEffect: (effect: Effect) -> Unit,
) {
    val consumeEffectState by rememberUpdatedState(consumeEffect)

    LaunchedEffect(Unit) {
        flow.collect {
            consumeEffectState(it)
        }
    }
}
