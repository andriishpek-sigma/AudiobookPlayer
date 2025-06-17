package com.testapp.audiobookplayer.presentation.feature.player.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.testapp.audiobookplayer.presentation.feature.player.AudiobookPlayerIntent
import com.testapp.audiobookplayer.presentation.feature.player.AudiobookPlayerState

@Composable
fun AudiobookPlayerScreenContent(
    state: AudiobookPlayerState,
    dispatch: (AudiobookPlayerIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
}
