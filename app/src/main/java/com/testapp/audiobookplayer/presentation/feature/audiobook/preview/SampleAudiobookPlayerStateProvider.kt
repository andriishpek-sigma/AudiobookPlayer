package com.testapp.audiobookplayer.presentation.feature.audiobook.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.testapp.audiobookplayer.presentation.feature.audiobook.AudiobookPlayerState
import com.testapp.audiobookplayer.presentation.util.asUiList

class SampleAudiobookPlayerStateProvider : PreviewParameterProvider<AudiobookPlayerState> {

    companion object {

        fun loading() = AudiobookPlayerState(
            isLoading = true,
        )

        fun loaded() = AudiobookPlayerState(
            book = AudiobookPlayerState.Book(
                imageUrl = "",
                name = "Some book name",
                author = "Some author",
            ),
            chapters = List(4) {
                AudiobookPlayerState.Chapter(
                    id = it.toString(),
                    label = "Chapter ${it + 1} label",
                    audioUrl = "",
                )
            }.asUiList(),
        )
    }

    override val values: Sequence<AudiobookPlayerState> = sequenceOf(
        loading(),
        loaded(),
    )
}
