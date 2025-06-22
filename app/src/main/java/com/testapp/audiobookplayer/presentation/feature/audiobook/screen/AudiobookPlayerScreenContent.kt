package com.testapp.audiobookplayer.presentation.feature.audiobook.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.media3.session.MediaController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.testapp.audiobookplayer.R
import com.testapp.audiobookplayer.presentation.feature.audiobook.AudiobookPlayerIntent
import com.testapp.audiobookplayer.presentation.feature.audiobook.AudiobookPlayerState
import com.testapp.audiobookplayer.presentation.feature.audiobook.preview.SampleAudiobookPlayerStateProvider
import com.testapp.audiobookplayer.presentation.theme.AudiobookPlayerTheme
import com.testapp.audiobookplayer.presentation.util.UiList
import com.testapp.audiobookplayer.presentation.util.media3.rememberContentDurationState
import com.testapp.audiobookplayer.presentation.util.media3.rememberCurrentMediaItemIndexState
import com.testapp.audiobookplayer.presentation.util.media3.rememberCurrentMediaItemSeekState
import com.testapp.audiobookplayer.presentation.util.media3.rememberLiveContentPositionState
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

@Composable
fun AudiobookPlayerScreenContent(
    state: AudiobookPlayerState,
    mediaControllerState: State<MediaController?>,
    dispatch: (AudiobookPlayerIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        ActualContent(
            modifier = Modifier.windowInsetsPadding(
                insets = WindowInsets.systemBars.union(WindowInsets.displayCutout),
            ),
            state = state,
            mediaControllerState = mediaControllerState,
            dispatch = dispatch,
        )
    }
}

@Composable
private fun ActualContent(
    state: AudiobookPlayerState,
    mediaControllerState: State<MediaController?>,
    dispatch: (AudiobookPlayerIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        HeaderContent(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            book = state.book,
        )

        BottomContent(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 32.dp),
            chapters = state.chapters,
            mediaControllerState = mediaControllerState,
        )

        AudiobookPlayerModeSwitch(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            isAudioModeSelected = state.isAudioMode,
            onAudioModeSelectionChange = {
                dispatch(AudiobookPlayerIntent.ChangeMode(it))
            },
        )
    }
}

@Composable
private fun HeaderContent(
    book: AudiobookPlayerState.Book?,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(8.dp)

    val placeholderColor = MaterialTheme.colorScheme.surfaceContainerHigh
    val placeholder = remember(placeholderColor) { ColorPainter(placeholderColor) }

    AsyncImage(
        modifier = modifier
            .aspectRatio(ratio = 2f / 3f, matchHeightConstraintsFirst = true)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = shape,
            )
            .clip(shape),
        model = ImageRequest.Builder(LocalContext.current)
            .data(book?.imageUrl)
            .crossfade(durationMillis = 150)
            .build(),
        contentDescription = book?.name,
        placeholder = placeholder,
        error = placeholder,
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun BottomContent(
    chapters: UiList<AudiobookPlayerState.Chapter>?,
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    val currentChapterIndexState = mediaControllerState.value?.let {
        rememberCurrentMediaItemIndexState(it)
    }
    val currentChapterIndex = currentChapterIndexState?.value

    val currentChapter = chapters?.let {
        if (currentChapterIndex == null) {
            return@let null
        }
        it.getOrNull(currentChapterIndex)
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KeyPointTitle(
            currentNumber = currentChapterIndex?.let { it + 1 } ?: 0,
            totalNumber = chapters?.size,
        )

        KeyPointLabel(
            modifier = Modifier.padding(top = 8.dp),
            label = currentChapter?.label,
        )

        AudioTimeContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            mediaControllerState = mediaControllerState,
        )

        AudiobookPlayerPlaybackSpeedButton(
            modifier = Modifier.padding(top = 16.dp),
            mediaControllerState = mediaControllerState,
        )

        AudiobookPlayerButtonControls(
            modifier = Modifier.padding(top = 32.dp),
            mediaControllerState = mediaControllerState,
        )
    }
}

@Composable
private fun KeyPointTitle(
    currentNumber: Int,
    totalNumber: Int?,
    modifier: Modifier = Modifier,
) {
    val text = stringResource(
        R.string.audiobook_player_key_point_title,
        currentNumber,
        totalNumber ?: 0,
    ).uppercase()

    Text(
        modifier = modifier,
        text = text,
        color = AudiobookPlayerTheme.colors.secondaryContent,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
private fun KeyPointLabel(
    label: String?,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = label ?: "",
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun AudioTimeContent(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    val seekState = mediaControllerState.value?.let {
        rememberCurrentMediaItemSeekState(it)
    }
    val durationState = mediaControllerState.value?.let {
        rememberContentDurationState(it)
    }
    val livePositionState = mediaControllerState.value?.let {
        rememberLiveContentPositionState(player = it, updatePeriodMillis = 25)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AudioTimeText(
            timeValueMillis = livePositionState?.value ?: 0,
        )

        AudiobookPlayerProgressSlider(
            modifier = Modifier.weight(1f),
            enabled = seekState?.isEnabled == true,
            position = livePositionState?.value ?: 0,
            duration = durationState?.value ?: 0,
            updatePosition = { position ->
                seekState?.seekTo(position)
            },
        )

        AudioTimeText(
            timeValueMillis = durationState?.value ?: 0,
        )
    }
}

@Composable
private fun AudioTimeText(
    timeValueMillis: Long,
    modifier: Modifier = Modifier,
) {
    val text = remember(timeValueMillis) {
        formatAudioTime(timeValueMillis.milliseconds)
    }

    Text(
        modifier = modifier,
        text = text,
        color = AudiobookPlayerTheme.colors.secondaryContent,
        style = MaterialTheme.typography.bodySmall,
    )
}

private fun formatAudioTime(duration: Duration): String {
    val minutes = duration.inWholeMinutes
    val seconds = (duration - minutes.minutes).inWholeSeconds

    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(SampleAudiobookPlayerStateProvider::class)
    state: AudiobookPlayerState,
) {
    AudiobookPlayerTheme {
        AudiobookPlayerScreenContent(
            state = state,
            mediaControllerState = remember { mutableStateOf(null) },
            dispatch = {},
        )
    }
}
