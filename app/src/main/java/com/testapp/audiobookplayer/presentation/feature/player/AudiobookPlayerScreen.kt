package com.testapp.audiobookplayer.presentation.feature.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.testapp.audiobookplayer.presentation.feature.player.screen.AudiobookPlayerScreenContent
import com.testapp.audiobookplayer.presentation.mvi.ConsumeEffects
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AudiobookPlayerScreen(
    bookId: String,
    modifier: Modifier = Modifier,
    store: AudiobookPlayerViewModel = koinViewModel { parametersOf(bookId) },
) {
    val state by store.observeState().collectAsStateWithLifecycle()

    ConsumeEffects(store.observeEffect()) { effect ->
//        when (effect) {
//        }
    }

    AudiobookPlayerScreenContent(
        modifier = modifier,
        state = state,
        dispatch = store::dispatch,
    )
}
