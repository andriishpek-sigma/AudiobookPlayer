package com.testapp.audiobookplayer.data.feature.book

import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.domain.feature.book.repository.BookRepository
import org.koin.core.annotation.Single
import kotlinx.coroutines.delay

// Ideally should be a 'reusable' factory (repo should be stateless), but koin doesn't support that
@Single
class BookRepositoryImpl(
    private val bookMockProvider: BookMockProvider,
) : BookRepository {

    override suspend fun getBook(id: BookId): Result<Book> {
        delay(500)
        return Result.success(
            bookMockProvider.provideMockedBook(id),
        )
    }

    override suspend fun getBookChapters(bookId: BookId): Result<List<BookChapter>> {
        delay(1000)
        return Result.success(
            bookMockProvider.provideMockedChapters(bookId),
        )
    }
}
