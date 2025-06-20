@file:OptIn(UnstableApi::class)

package com.testapp.audiobookplayer.presentation.feature.audiobook.screen

import androidx.annotation.OptIn
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forward10
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Replay5
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.ui.compose.state.rememberNextButtonState
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import androidx.media3.ui.compose.state.rememberPreviousButtonState
import com.testapp.audiobookplayer.R
import com.testapp.audiobookplayer.presentation.theme.AudiobookPlayerTheme

@Composable
fun AudiobookPlayerButtonControls(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PreviousTrackButton(
            mediaControllerState = mediaControllerState,
        )

        ReplayButton(
            mediaControllerState = mediaControllerState,
        )

        PlayPauseButton(
            mediaControllerState = mediaControllerState,
        )

        ForwardButton(
            mediaControllerState = mediaControllerState,
        )

        NextTrackButton(
            mediaControllerState = mediaControllerState,
        )
    }
}

@Composable
private fun PlayPauseButton(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    val playPauseState = mediaControllerState.value?.let {
        rememberPlayPauseButtonState(it)
    }
    val isPlaying = playPauseState?.showPlay == false

    PlayerControlButton(
        modifier = modifier,
        onClick = {
            playPauseState?.onClick()
        },
        // TODO maybe progress indicator if disabled?
        enabled = playPauseState?.isEnabled == true,
        image = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
        contentDescriptionRes = if (isPlaying) {
            R.string.player_pause_description
        } else {
            R.string.player_play_description
        },
        customSize = 56.dp,
    )
}

@Composable
private fun ReplayButton(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    PlayerControlButton(
        modifier = modifier,
        onClick = {
            mediaControllerState.value?.seekBack()
        },
        enabled = true,
        image = Icons.Rounded.Replay5,
        contentDescriptionRes = R.string.player_replay_5_description,
    )
}

@Composable
private fun ForwardButton(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    PlayerControlButton(
        modifier = modifier,
        onClick = {
            mediaControllerState.value?.seekForward()
        },
        enabled = true,
        image = Icons.Rounded.Forward10,
        contentDescriptionRes = R.string.player_forward_10_description,
    )
}

@Composable
private fun PreviousTrackButton(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    val previousButtonState = mediaControllerState.value?.let {
        rememberPreviousButtonState(it)
    }

    PlayerControlButton(
        modifier = modifier,
        onClick = {
            previousButtonState?.onClick()
        },
        enabled = previousButtonState?.isEnabled == true,
        image = Icons.Rounded.SkipPrevious,
        contentDescriptionRes = R.string.player_skip_previous_description,
    )
}

@Composable
private fun NextTrackButton(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    val nextButtonState = mediaControllerState.value?.let {
        rememberNextButtonState(it)
    }

    PlayerControlButton(
        modifier = modifier,
        onClick = {
            nextButtonState?.onClick()
        },
        enabled = nextButtonState?.isEnabled == true,
        image = Icons.Rounded.SkipNext,
        contentDescriptionRes = R.string.player_skip_next_description,
    )
}

@Composable
private fun PlayerControlButton(
    onClick: () -> Unit,
    enabled: Boolean,
    image: ImageVector,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier,
    customSize: Dp? = null,
) {
    val haptic = LocalHapticFeedback.current

    IconButton(
        modifier = modifier,
        onClick = {
            onClick()
            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
        },
        enabled = enabled,
    ) {
        val customSizeModifier = customSize?.let {
            Modifier.requiredSize(it)
        } ?: Modifier

        Image(
            modifier = Modifier
                .fillMaxSize()
                .then(customSizeModifier),
            imageVector = image,
            contentDescription = stringResource(contentDescriptionRes),
            colorFilter = ColorFilter.tint(LocalContentColor.current),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AudiobookPlayerTheme {
        AudiobookPlayerButtonControls(
            mediaControllerState = remember { mutableStateOf(null) },
        )
    }
}
