package com.testapp.audiobookplayer.domain.feature.book.usecase

import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import com.testapp.audiobookplayer.domain.feature.book.repository.BookRepository
import org.koin.core.annotation.Factory

interface GetBookChaptersUseCase {

    suspend operator fun invoke(bookId: BookId): Result<List<BookChapter>>
}

@Factory
class GetBookChaptersUseCaseImpl(
    private val repository: BookRepository,
) : GetBookChaptersUseCase {

    override suspend fun invoke(bookId: BookId): Result<List<BookChapter>> {
        return repository.getBookChapters(bookId)
    }
}
