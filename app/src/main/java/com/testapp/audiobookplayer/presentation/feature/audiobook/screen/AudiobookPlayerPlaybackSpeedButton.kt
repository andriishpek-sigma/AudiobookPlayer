package com.testapp.audiobookplayer.presentation.feature.audiobook.screen

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.ui.compose.state.rememberPlaybackSpeedState
import com.testapp.audiobookplayer.R
import com.testapp.audiobookplayer.presentation.theme.AudiobookPlayerTheme
import java.text.DecimalFormat

private val PlaybackSpeedToggleMap = mapOf(
    1f to 1.5f,
    1.5f to 2f,
    2.5f to 0.5f,
    0.5f to 1f,
)

@OptIn(UnstableApi::class)
@Composable
fun AudiobookPlayerPlaybackSpeedButton(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    val playbackSpeedState = mediaControllerState.value?.let {
        rememberPlaybackSpeedState(it)
    }
    val currentPlaybackSpeed = playbackSpeedState?.playbackSpeed ?: 1f

    FilledTonalButton(
        modifier = modifier,
        onClick = {
            val nextPlaybackSpeed = PlaybackSpeedToggleMap[currentPlaybackSpeed]
                ?: return@FilledTonalButton
            playbackSpeedState?.updatePlaybackSpeed(nextPlaybackSpeed)
        },
        enabled = playbackSpeedState?.isEnabled == true,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(
            text = formatPlaybackSpeed(currentPlaybackSpeed),
        )
    }
}

@Composable
private fun formatPlaybackSpeed(speed: Float): String {
    val formattedSpeed = remember(speed) {
        DecimalFormat.getInstance().apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }.format(speed.toDouble())
    }

    return stringResource(
        R.string.audiobook_player_playback_speed_button,
        formattedSpeed,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPadded() {
    AudiobookPlayerTheme {
        AudiobookPlayerPlaybackSpeedButton(
            modifier = Modifier.padding(16.dp),
            mediaControllerState = remember { mutableStateOf(null) },
        )
    }
}
