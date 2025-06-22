package com.testapp.audiobookplayer.presentation.util.media3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.media3.common.Player
import androidx.media3.common.listen

@Composable
fun rememberPlayerIsLoadingState(player: Player): PlayerIsLoadingState {
    val playerIsLoadingState = remember(player) { PlayerIsLoadingState(player) }
    LaunchedEffect(player) { playerIsLoadingState.observe() }
    return playerIsLoadingState
}

class PlayerIsLoadingState(
    private val player: Player,
) {

    var value by mutableStateOf(resolvePlayerIsLoading())
        private set

    suspend fun observe() {
        player.listen { events ->
            if (
                events.containsAny(
                    Player.EVENT_IS_LOADING_CHANGED
                )
            ) {
                value = resolvePlayerIsLoading()
            }
        }
    }

    private fun resolvePlayerIsLoading(): Boolean {
        return player.isLoading
    }
}
