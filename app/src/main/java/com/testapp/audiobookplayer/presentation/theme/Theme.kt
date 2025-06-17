package com.testapp.audiobookplayer.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AudiobookPlayerTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = AudiobookColorScheme.resolve(isDarkTheme = isDarkTheme)

    MaterialTheme(
        colorScheme = colorScheme.material,
        typography = Typography,
    ) {
        CompositionLocalProvider(
            LocalAppColorScheme provides colorScheme,
            content = content,
        )
    }
}

object AudiobookPlayerTheme {

    val colors: AudiobookColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColorScheme.current
}
