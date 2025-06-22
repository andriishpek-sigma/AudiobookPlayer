package com.testapp.audiobookplayer.presentation.feature.player

import androidx.annotation.OptIn
import androidx.media3.common.ForwardingSimpleBasePlayer
import androidx.media3.common.Player
import androidx.media3.common.Player.COMMAND_SEEK_TO_PREVIOUS
import androidx.media3.common.util.UnstableApi

/**
 * Customizes the following:
 * - Disables [COMMAND_SEEK_TO_PREVIOUS] while playing initial media item;
 */
@OptIn(UnstableApi::class)
class AudiobookPlayer(
    player: Player,
) : ForwardingSimpleBasePlayer(player) {

    override fun getState(): State {
        val state = super.getState()

        val filteredCommands = state.availableCommands.buildUpon()
            .removeSeekToPreviousIfPlayingInitialItem()
            .build()

        return state.buildUpon()
            .setAvailableCommands(filteredCommands)
            .build()
    }

    private fun Player.Commands.Builder.removeSeekToPreviousIfPlayingInitialItem() = apply {
        if (player.currentMediaItemIndex == 0) {
            remove(COMMAND_SEEK_TO_PREVIOUS)
        }
    }
}
