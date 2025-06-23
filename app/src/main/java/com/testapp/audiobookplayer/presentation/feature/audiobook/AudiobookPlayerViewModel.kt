package com.testapp.audiobookplayer.presentation.feature.audiobook

import androidx.lifecycle.viewModelScope
import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.domain.feature.book.usecase.GetBookChaptersUseCase
import com.testapp.audiobookplayer.domain.feature.book.usecase.GetBookUseCase
import com.testapp.audiobookplayer.presentation.mvi.MviViewModel
import com.testapp.audiobookplayer.presentation.mvi.dispatch
import com.testapp.audiobookplayer.presentation.util.asUiList
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import kotlinx.coroutines.launch

@KoinViewModel
class AudiobookPlayerViewModel(
    @InjectedParam val bookId: BookId,
    private val getBookUseCase: GetBookUseCase,
    private val getBookChaptersUseCase: GetBookChaptersUseCase,
) : MviViewModel<AudiobookPlayerState, AudiobookPlayerIntent, AudiobookPlayerEffect>(
    emptyState = AudiobookPlayerState(),
) {

    init {
        dispatch(
            AudiobookPlayerIntent.LoadData(bookId = bookId),
        )
    }

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
            launch { loadBook() }
            launch { loadBookChapters() }
        }
    }

    private suspend fun loadBook() {
        val book = getBookUseCase(bookId).getOrElse {
            // TODO error handling
            return
        }

        val uiBook = book.toUiBook()

        dispatch {
            AudiobookPlayerIntent.UpdateBookData(uiBook)
        }
    }

    private suspend fun loadBookChapters() {
        val chapters = getBookChaptersUseCase(bookId).getOrElse {
            // TODO error handling
            return
        }

        val uiChapters = chapters.map { it.toUiChapter() }.asUiList()

        dispatch {
            AudiobookPlayerIntent.UpdateChapterData(uiChapters)
        }
    }

    private fun Book.toUiBook() = AudiobookPlayerState.Book(
        imageUrl = imageUrl,
        name = name,
        author = author,
    )

    private fun BookChapter.toUiChapter() = AudiobookPlayerState.Chapter(
        id = id.value.toString(),
        label = label,
        audioUrl = audioUrl,
    )
}
