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
fun rememberCurrentMediaItemSeekState(player: Player): CurrentMediaItemSeekState {
    val currentMediaItemSeekState = remember(player) { CurrentMediaItemSeekState(player) }
    LaunchedEffect(player) { currentMediaItemSeekState.observe() }
    return currentMediaItemSeekState
}

class CurrentMediaItemSeekState(
    private val player: Player,
) {

    var isEnabled by mutableStateOf(resolveIsEnabled())
        private set

    fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    suspend fun observe() {
        player.listen { events ->
            if (
                events.containsAny(
                    Player.EVENT_AVAILABLE_COMMANDS_CHANGED,
                )
            ) {
                isEnabled = resolveIsEnabled()
            }
        }
    }

    private fun resolveIsEnabled(): Boolean {
        return player.isCommandAvailable(Player.COMMAND_SEEK_IN_CURRENT_MEDIA_ITEM)
    }
}
