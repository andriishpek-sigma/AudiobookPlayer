package com.testapp.audiobookplayer.presentation.feature.audiobook

import com.testapp.audiobookplayer.presentation.mvi.UiState
import com.testapp.audiobookplayer.presentation.util.UiList

data class AudiobookPlayerState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val chapters: UiList<Chapter>? = null,
    val isAudioMode: Boolean = true,
) : UiState {

    data class Book(
        val imageUrl: String,
        val name: String,
        val author: String,
    )

    data class Chapter(
        val id: String,
        val label: String,
        val audioUrl: String,
    )
}
