package com.testapp.audiobookplayer.presentation.util.media3

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(UnstableApi::class)
@Composable
fun rememberLiveContentPositionState(
    player: Player,
    updatePeriodMillis: Long = 1000,
): State<Long?> {
    val contentPositionState = rememberContentPositionState(player)

    val liveContentPositionState = remember { mutableStateOf(contentPositionState.value) }

    val playPauseState = rememberPlayPauseButtonState(player)
    val isPlaying = !playPauseState.showPlay

    // Sync state wrapper with content position state
    LaunchedEffect(contentPositionState.value) {
        liveContentPositionState.value = contentPositionState.value
    }

    // Periodically update content position
    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            // Sync up-to-date position when playback ends
            contentPositionState.update()
            return@LaunchedEffect
        }

        while (isActive) {
            contentPositionState.update()
            delay(updatePeriodMillis)
        }
    }

    return liveContentPositionState
}
