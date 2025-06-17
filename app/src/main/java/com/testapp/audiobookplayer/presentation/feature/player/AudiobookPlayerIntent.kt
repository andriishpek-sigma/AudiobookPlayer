package com.testapp.audiobookplayer.presentation.feature.player

import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.presentation.mvi.UiIntent
import com.testapp.audiobookplayer.presentation.util.UiList

sealed class AudiobookPlayerIntent : UiIntent {

    data class LoadData(
        val bookId: BookId,
    ) : AudiobookPlayerIntent()

    data class UpdateBookData(
        val book: AudiobookPlayerState.Book,
    ) : AudiobookPlayerIntent()

    data class UpdateChapterData(
        val chapters: UiList<AudiobookPlayerState.Chapter>,
    ) : AudiobookPlayerIntent()

    data class ChangeMode(
        val isAudioMode: Boolean,
    ) : AudiobookPlayerIntent()
}
