package com.testapp.audiobookplayer.presentation.util.media3

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.testapp.audiobookplayer.presentation.util.UiList

/**
 * Updates existing media items without playback interruption if possible, otherwise updates
 * all media items.
 */
fun Player.smartUpdateMediaItems(
    mediaItems: UiList<MediaItem>,
) {
    // Try to match with existing media items and update each
    val areExistingMediaItemsUpdated = tryUpdateExistingMediaItems(mediaItems)
    if (areExistingMediaItemsUpdated) {
        return
    }

    // Otherwise replace all media items
    setMediaItems(mediaItems)
}

private fun Player.tryUpdateExistingMediaItems(
    mediaItems: UiList<MediaItem>,
): Boolean {
    // Don't update if media items count is different
    if (mediaItems.size == 0 || mediaItems.size != mediaItemCount) {
        return false
    }

    // Don't update if media item ids don't match
    repeat(mediaItems.size) { index ->
        if (mediaItems[index].mediaId != getMediaItemAt(index).mediaId) {
            return false
        }
    }

    mediaItems.forEachIndexed { index, item ->
        replaceMediaItem(index, item)
    }

    return true
}
