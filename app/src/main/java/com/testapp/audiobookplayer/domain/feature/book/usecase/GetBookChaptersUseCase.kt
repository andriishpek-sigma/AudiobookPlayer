package com.testapp.audiobookplayer.domain.feature.book.usecase

import com.testapp.audiobookplayer.domain.feature.book.model.BookChapter
import com.testapp.audiobookplayer.domain.feature.book.model.BookId
import org.koin.core.annotation.Factory

interface GetBookChaptersUseCase {

    suspend operator fun invoke(id: BookId): Result<List<BookChapter>>
}

@Factory
class GetBookChaptersUseCaseImpl : GetBookChaptersUseCase {

    override suspend fun invoke(id: BookId): Result<List<BookChapter>> {
        TODO("Not yet implemented")
    }
}
