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
fun rememberContentPositionState(player: Player): ContentPositionState {
    val contentPositionState = remember(player) { ContentPositionState(player) }
    LaunchedEffect(player) { contentPositionState.observe() }
    return contentPositionState
}

class ContentPositionState(
    private val player: Player,
) {

    var value by mutableStateOf(resolveContentPosition())
        private set

    suspend fun observe() {
        player.listen { events ->
            if (
                events.containsAny(
                    Player.EVENT_MEDIA_ITEM_TRANSITION,
                    Player.EVENT_POSITION_DISCONTINUITY,
                    Player.EVENT_AVAILABLE_COMMANDS_CHANGED,
                )
            ) {
                update()
            }
        }
    }

    fun update() {
        value = resolveContentPosition()
    }

    private fun resolveContentPosition(): Long? {
        if (!player.isCommandAvailable(Player.COMMAND_GET_CURRENT_MEDIA_ITEM)) {
            return null
        }
        return player.contentPosition
    }
}
