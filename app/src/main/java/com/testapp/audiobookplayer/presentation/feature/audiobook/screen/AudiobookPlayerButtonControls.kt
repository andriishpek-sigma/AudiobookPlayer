@file:OptIn(UnstableApi::class)

package com.testapp.audiobookplayer.presentation.feature.audiobook.screen

import androidx.annotation.OptIn
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forward10
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Replay5
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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
import com.testapp.audiobookplayer.presentation.util.media3.rememberPlayerIsLoadingState

@Composable
fun AudiobookPlayerButtonControls(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        PreviousTrackButton(
            mediaControllerState = mediaControllerState,
        )

        ReplayButton(
            mediaControllerState = mediaControllerState,
        )

        PlayPauseButtonWithLoader(
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
private fun PlayPauseButtonWithLoader(
    mediaControllerState: State<MediaController?>,
    modifier: Modifier = Modifier,
) {
    val playerIsLoadingState = mediaControllerState.value?.let {
        rememberPlayerIsLoadingState(it)
    }
    val isLoading = playerIsLoadingState?.value != false

    Box(
        modifier = modifier,
        propagateMinConstraints = true,
    ) {
        AnimatedVisibility(
            modifier = Modifier.matchParentSize(),
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            CircularProgressIndicator()
        }

        PlayPauseButton(
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
        imagePadding = 4.dp,
        contentDescriptionRes = if (isPlaying) {
            R.string.player_pause_description
        } else {
            R.string.player_play_description
        },
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
        imagePadding = 12.dp,
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
        imagePadding = 12.dp,
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
        imagePadding = 12.dp,
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
        imagePadding = 12.dp,
        contentDescriptionRes = R.string.player_skip_next_description,
    )
}

@Composable
private fun PlayerControlButton(
    onClick: () -> Unit,
    enabled: Boolean,
    image: ImageVector,
    imagePadding: Dp,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    // Custom implementation of IconButton to fine tune icon sizes
    Box(
        modifier = modifier
            .size(64.dp)
            .minimumInteractiveComponentSize()
            .clip(CircleShape)
            .clickable(
                onClick = {
                    onClick()
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                },
                enabled = enabled,
                role = Role.Button,
            )
            .padding(imagePadding),
        contentAlignment = Alignment.Center,
    ) {
        val enabledColor = LocalContentColor.current
        val currentColor = if (enabled) enabledColor else enabledColor.copy(alpha = 0.4f)

        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = image,
            contentDescription = stringResource(contentDescriptionRes),
            colorFilter = ColorFilter.tint(currentColor),
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
