package com.testapp.audiobookplayer.presentation.util.media3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.listen

@Composable
fun rememberDurationState(player: Player): DurationState {
    val durationState = remember(player) { DurationState(player) }
    LaunchedEffect(player) { durationState.observe() }
    return durationState
}

class DurationState(
    private val player: Player,
) {

    var value by mutableStateOf(resolveDuration())
        private set

    suspend fun observe() {
        player.listen { events ->
            if (
                events.containsAny(
                    Player.EVENT_MEDIA_ITEM_TRANSITION,
                    Player.EVENT_PLAYBACK_STATE_CHANGED,
                    Player.EVENT_AVAILABLE_COMMANDS_CHANGED,
                )
            ) {
                value = resolveDuration()
            }
        }
    }

    private fun resolveDuration(): Long? {
        if (!player.isCommandAvailable(Player.COMMAND_GET_CURRENT_MEDIA_ITEM)) {
            return null
        }
        return player.duration.takeIf { it != C.TIME_UNSET }
    }
}
