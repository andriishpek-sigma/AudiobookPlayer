package com.testapp.audiobookplayer.data.feature.book

import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapterId
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import org.koin.core.annotation.Factory

interface BookMockProvider {

    fun provideMockedBook(id: BookId): Book

    fun provideMockedChapters(bookId: BookId): List<BookChapter>
}

@Factory
class BookMockProviderImpl : BookMockProvider {

    override fun provideMockedBook(id: BookId): Book = Book(
        id = id,
        name = "Some book name",
        author = "Some Author",
        imageUrl = "https://4.img-dpreview.com/files/p/E~TS1180x0~articles/" +
            "3925134721/0266554465.jpeg",
    )

    override fun provideMockedChapters(bookId: BookId): List<BookChapter> = listOf(
        BookChapter(
            id = BookChapterId(1),
            label = "Mind reading is real",
            audioUrl = "https://download.samplelib.com/mp3/sample-6s.mp3",
        ),
        BookChapter(
            id = BookChapterId(2),
            label = "It's all about context and attentiveness",
            audioUrl = "https://download.samplelib.com/mp3/sample-9s.mp3",
        ),
        BookChapter(
            id = BookChapterId(3),
            label = "Identifying true motives",
            audioUrl = "https://download.samplelib.com/mp3/sample-12s.mp3",
        ),
        BookChapter(
            id = BookChapterId(4),
            label = "Silent expressions speak loader than words",
            audioUrl = "https://download.samplelib.com/mp3/sample-15s.mp3",
        ),
    )
}
