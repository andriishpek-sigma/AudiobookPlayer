@file:OptIn(ExperimentalCoroutinesApi::class)

package com.testapp.audiobookplayer.presentation.feature.audiobook

import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapterId
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.domain.feature.book.usecase.GetBookChaptersUseCase
import com.testapp.audiobookplayer.domain.feature.book.usecase.GetBookUseCase
import com.testapp.audiobookplayer.presentation.mvi.dispatch
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AudiobookPlayerViewModelTest {

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Loading initial data updates state loading field to true`() {
        val viewModel = AudiobookPlayerViewModel(
            bookId = BookId(1),
            getBookUseCase = mockk(relaxed = true),
            getBookChaptersUseCase = mockk(relaxed = true),
        )

        viewModel.dispatch(AudiobookPlayerIntent.LoadData)

        assertTrue { viewModel.observeState().value.isLoading }
    }

    @Test
    fun `When book is loaded, state is updated with proper data`() {
        val book = fakeBook()

        val getBookUseCase = object : GetBookUseCase {
            override suspend fun invoke(id: BookId): Result<Book> = Result.success(book)
        }

        val viewModel = AudiobookPlayerViewModel(
            bookId = BookId(1),
            getBookUseCase = getBookUseCase,
            getBookChaptersUseCase = mockk(relaxed = true),
        )

        viewModel.dispatch(AudiobookPlayerIntent.LoadData)

        val state = viewModel.observeState().value

        assertEquals(book.imageUrl, state.book?.imageUrl)
        assertEquals(book.name, state.book?.name)
        assertEquals(book.author, state.book?.author)
    }

    @Test
    fun `When chapters are loaded, state is updated with proper data`() {
        val book = fakeBook()
        val chapters = fakeBookChapters()

        val getBookUseCase = object : GetBookUseCase {
            override suspend fun invoke(id: BookId): Result<Book> = Result.success(book)
        }

        val getBookChaptersUseCase = object : GetBookChaptersUseCase {
            override suspend fun invoke(id: BookId): Result<List<BookChapter>> =
                Result.success(chapters)
        }

        val viewModel = AudiobookPlayerViewModel(
            bookId = BookId(1),
            getBookUseCase = getBookUseCase,
            getBookChaptersUseCase = getBookChaptersUseCase,
        )

        viewModel.dispatch(AudiobookPlayerIntent.LoadData)

        val state = viewModel.observeState().value

        assertEquals(chapters.size, state.chapters?.size)
        chapters.forEachIndexed { index, chapter ->
            val uiChapter = assertNotNull(state.chapters?.get(index))

            assertEquals(chapter.id.value.toString(), uiChapter.id)
            assertEquals(chapter.label, uiChapter.label)
            assertEquals(chapter.audioUrl, uiChapter.audioUrl)
        }

        assertFalse { state.isLoading }
    }

    @Test
    fun `Changing mode updates state`() {
        val isAudioMode = false

        val viewModel = AudiobookPlayerViewModel(
            bookId = BookId(1),
            getBookUseCase = mockk(),
            getBookChaptersUseCase = mockk(),
        )

        viewModel.dispatch { AudiobookPlayerIntent.ChangeMode(isAudioMode = isAudioMode) }

        assertEquals(isAudioMode, viewModel.observeState().value.isAudioMode)
    }

    private fun fakeBook() = Book(
        id = BookId(1),
        name = "Name",
        author = "Author",
        imageUrl = "image",
    )

    fun fakeBookChapters() = List(4) { fakeBookChapter(it) }

    fun fakeBookChapter(id: Int) = BookChapter(
        id = BookChapterId(id),
        label = "Chapter $id",
        audioUrl = "audio",
    )
}
