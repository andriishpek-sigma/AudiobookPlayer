package com.testapp.audiobookplayer.data.feature.book

import com.testapp.audiobookplayer.app.DispatcherType
import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.domain.feature.book.repository.BookRepository
import org.koin.core.annotation.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// Ideally should be a 'reusable' factory (repo should be stateless), but koin doesn't support that
@Single
class BookRepositoryImpl(
    private val bookMockProvider: BookMockProvider,
    @DispatcherType.IO
    private val ioDispatcher: CoroutineDispatcher,
) : BookRepository {

    override suspend fun getBook(
        id: BookId,
    ): Result<Book> = withContext(ioDispatcher) {
        delay(500)
        Result.success(
            bookMockProvider.provideMockedBook(id),
        )
    }

    override suspend fun getBookChapters(
        bookId: BookId,
    ): Result<List<BookChapter>> = withContext(ioDispatcher) {
        delay(1000)
        Result.success(
            bookMockProvider.provideMockedChapters(bookId),
        )
    }
}
