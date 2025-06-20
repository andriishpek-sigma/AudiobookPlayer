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
fun rememberCurrentMediaItemIndexState(player: Player): CurrentMediaItemIndexState {
    val currentMediaItemIndexState = remember(player) { CurrentMediaItemIndexState(player) }
    LaunchedEffect(player) { currentMediaItemIndexState.observe() }
    return currentMediaItemIndexState
}

class CurrentMediaItemIndexState(
    private val player: Player,
) {

    var value by mutableStateOf(resolveCurrentMediaItemIndex())
        private set

    suspend fun observe() {
        player.listen { events ->
            if (
                events.containsAny(
                    Player.EVENT_MEDIA_ITEM_TRANSITION,
                    Player.EVENT_AVAILABLE_COMMANDS_CHANGED,
                )
            ) {
                value = resolveCurrentMediaItemIndex()
            }
        }
    }

    private fun resolveCurrentMediaItemIndex(): Int? {
        if (!player.isCommandAvailable(Player.COMMAND_GET_TIMELINE)) {
            return null
        }
        return player.currentMediaItemIndex
    }
}
