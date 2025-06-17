package com.testapp.audiobookplayer.presentation.feature.player

import com.testapp.audiobookplayer.presentation.mvi.UiState
import com.testapp.audiobookplayer.presentation.util.UiList

data class AudiobookPlayerState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val chapters: UiList<Chapter>? = null,
) : UiState {

    data class Book(
        val imageUrl: String,
    )

    data class Chapter(
        val id: String,
        val label: String,
        val audioUrl: String,
    )
}
