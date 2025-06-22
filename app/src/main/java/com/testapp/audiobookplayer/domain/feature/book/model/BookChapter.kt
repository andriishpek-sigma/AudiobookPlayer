package com.testapp.audiobookplayer.domain.feature.book.model

data class BookChapter(
    val id: BookChapterId,
    val label: String,
    val audioUrl: String,
)
