package com.testapp.audiobookplayer.domain.feature.book.usecase

import com.testapp.audiobookplayer.domain.feature.book.model.Book
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import org.koin.core.annotation.Factory

interface GetBookUseCase {

    suspend operator fun invoke(id: BookId): Result<Book>
}

@Factory
class GetBookUseCaseImpl : GetBookUseCase {

    override suspend fun invoke(id: BookId): Result<Book> {
        TODO("Not yet implemented")
    }
}
