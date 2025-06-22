package com.testapp.audiobookplayer.domain.feature.book.model

data class Book(
    val id: BookId,
    val name: String,
    val author: String,
    val imageUrl: String,
)
