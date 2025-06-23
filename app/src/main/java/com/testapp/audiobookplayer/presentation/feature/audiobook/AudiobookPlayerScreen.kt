package com.testapp.audiobookplayer.presentation.feature.audiobook

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionError
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.presentation.feature.audiobook.screen.AudiobookPlayerScreenContent
import com.testapp.audiobookplayer.presentation.feature.player.AudiobookPlayerService
import com.testapp.audiobookplayer.presentation.mvi.ConsumeEffects
import com.testapp.audiobookplayer.presentation.util.media3.rememberMediaControllerStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AudiobookPlayerScreen(
    bookId: Int,
    modifier: Modifier = Modifier,
    store: AudiobookPlayerViewModel = koinViewModel { parametersOf(BookId(bookId)) },
) {
    val state by store.observeState().collectAsStateWithLifecycle()

    val audiobookMediaControllerState = rememberMediaControllerStateWithLifecycle(
        isEnabled = state.isPlayerDataLoaded,
        classProvider = { AudiobookPlayerService::class.java },
        listener = { AudiobookMediaControllerListener() },
    )

    // Enable when effects are added to the screen
//    ConsumeEffects(store.observeEffect()) { effect ->
//        when (effect) {
//        }
//    }

    AudiobookPlayerScreenContent(
        modifier = modifier,
        state = state,
        mediaControllerState = audiobookMediaControllerState,
        dispatch = store::dispatch,
    )

    StartAudiobookPlaybackWhenLoaded(
        state = state,
        audiobookMediaControllerState = audiobookMediaControllerState,
    )
}

@Composable
private fun StartAudiobookPlaybackWhenLoaded(
    state: AudiobookPlayerState,
    audiobookMediaControllerState: State<MediaController?>,
) {
    val book = state.book ?: return
    val chapters = state.chapters?.takeIf { it.isNotEmpty() } ?: return

    val audiobookMediaController = audiobookMediaControllerState.value ?: return

    LaunchedEffect(audiobookMediaController, book, chapters) {
        val mediaItems = chapters.map {
            createMediaItem(
                book = book,
                chapter = it,
            )
        }

        audiobookMediaController.setMediaItems(mediaItems)
        audiobookMediaController.repeatMode = Player.REPEAT_MODE_OFF
        audiobookMediaController.prepare()
        audiobookMediaController.play()
    }
}

private fun createMediaItem(
    book: AudiobookPlayerState.Book,
    chapter: AudiobookPlayerState.Chapter,
): MediaItem {
    return MediaItem.Builder()
        .setMediaId(chapter.id)
        .setUri(chapter.audioUrl.toUri())
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setArtist(book.author)
                .setTitle(book.name)
                .setSubtitle(chapter.label)
                .setArtworkUri(book.imageUrl.toUri())
                .build(),
        )
        .build()
}

@OptIn(UnstableApi::class)
private class AudiobookMediaControllerListener : MediaController.Listener {

    override fun onError(controller: MediaController, sessionError: SessionError) {
        val message = "MediaController error: ${sessionError.code} - ${sessionError.message}"
        Log.d("AudiobookPlayer", message)
    }
}
