package com.testapp.audiobookplayer.domain.feature.book.usecase

import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.domain.feature.book.repository.BookRepository
import org.koin.core.annotation.Factory

interface GetBookUseCase {

    suspend operator fun invoke(id: BookId): Result<Book>
}

@Factory
class GetBookUseCaseImpl(
    private val repository: BookRepository,
) : GetBookUseCase {

    override suspend fun invoke(id: BookId): Result<Book> {
        return repository.getBook(id)
    }
}
