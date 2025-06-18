package com.testapp.audiobookplayer.presentation.feature.audiobook

import androidx.lifecycle.viewModelScope
import com.testapp.audiobookplayer.presentation.mvi.MviViewModel
import com.testapp.audiobookplayer.presentation.mvi.dispatch
import com.testapp.audiobookplayer.presentation.util.uiListOf
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudiobookPlayerViewModel :
    MviViewModel<AudiobookPlayerState, AudiobookPlayerIntent, AudiobookPlayerEffect>(
        emptyState = AudiobookPlayerState(),
    ) {

    override fun reduce(
        state: AudiobookPlayerState,
        intent: AudiobookPlayerIntent,
    ): AudiobookPlayerState = when (intent) {
        is AudiobookPlayerIntent.LoadData -> state.copy(
            isLoading = true,
        ).also {
            loadData()
        }

        is AudiobookPlayerIntent.UpdateBookData -> state.copy(
            book = intent.book,
        )

        is AudiobookPlayerIntent.UpdateChapterData -> state.copy(
            isLoading = false,
            chapters = intent.chapters,
        )

        is AudiobookPlayerIntent.ChangeMode -> state.copy(
            isAudioMode = intent.isAudioMode,
        )
    }

    private fun loadData() {
        viewModelScope.launch {
            // TODO load actual data with flows

            async {
                delay(250)

                dispatch {
                    AudiobookPlayerIntent.UpdateBookData(
                        book = AudiobookPlayerState.Book(
                            imageUrl = "https://4.img-dpreview.com/files/p/E~TS1180x0~articles/3925134721/0266554465.jpeg",
                            name = "Some book name",
                        ),
                    )
                }
            }

            async {
                delay(500)

                dispatch {
                    AudiobookPlayerIntent.UpdateChapterData(
                        chapters = uiListOf(
                            AudiobookPlayerState.Chapter(
                                id = "1",
                                label = "Chapter 1",
                                audioUrl = "https://download.samplelib.com/mp3/sample-6s.mp3",
                            ),
                            AudiobookPlayerState.Chapter(
                                id = "2",
                                label = "Chapter 2",
                                audioUrl = "https://download.samplelib.com/mp3/sample-9s.mp3",
                            ),
                            AudiobookPlayerState.Chapter(
                                id = "3",
                                label = "Chapter 3",
                                audioUrl = "https://download.samplelib.com/mp3/sample-12s.mp3",
                            ),
                            AudiobookPlayerState.Chapter(
                                id = "4",
                                label = "Chapter 4",
                                audioUrl = "https://download.samplelib.com/mp3/sample-15s.mp3",
                            ),
                        ),
                    )
                }
            }
        }
    }
}
