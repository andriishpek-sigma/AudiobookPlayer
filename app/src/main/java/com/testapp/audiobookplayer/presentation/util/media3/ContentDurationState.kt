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
fun rememberContentDurationState(player: Player): ContentDurationState {
    val contentDurationState = remember(player) { ContentDurationState(player) }
    LaunchedEffect(player) { contentDurationState.observe() }
    return contentDurationState
}

class ContentDurationState(
    private val player: Player,
) {

    var value by mutableStateOf(resolveContentDuration())
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
                value = resolveContentDuration()
            }
        }
    }

    private fun resolveContentDuration(): Long? {
        if (!player.isCommandAvailable(Player.COMMAND_GET_CURRENT_MEDIA_ITEM)) {
            return null
        }
        return player.contentDuration.takeIf { it != C.TIME_UNSET }
    }
}
