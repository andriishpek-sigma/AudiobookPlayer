package com.testapp.audiobookplayer.domain.feature.book.repository

import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookId

interface BookRepository {

    suspend fun getBook(id: BookId): Result<Book>

    suspend fun getBookChapters(bookId: BookId): Result<List<BookChapter>>
}
