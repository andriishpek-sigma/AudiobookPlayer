package com.testapp.audiobookplayer.presentation.feature.audiobook.screen

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.FormatAlignLeft
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testapp.audiobookplayer.presentation.theme.AudiobookPlayerTheme

private val IconSize = 48.dp
private val IconPadding = 12.dp
private const val AnimationSpringStiffness = Spring.StiffnessMedium

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AudiobookPlayerModeSwitch(
    isAudioModeSelected: Boolean,
    onAudioModeSelectionChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = CircleShape

    LookaheadScope {
        Box(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = shape,
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = shape,
                )
                .clip(shape)
                .toggleable(
                    value = isAudioModeSelected,
                    onValueChange = onAudioModeSelectionChange,
                )
                .padding(4.dp),
        ) {
            SelectionIndicator(
                modifier = Modifier
                    .align(if (isAudioModeSelected) Alignment.CenterEnd else Alignment.CenterStart)
                    .animateBounds(
                        lookaheadScope = this@LookaheadScope,
                        boundsTransform = BoundsTransform { _, _ ->
                            spring(
                                stiffness = AnimationSpringStiffness,
                                visibilityThreshold = Rect.VisibilityThreshold,
                            )
                        },
                    ),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextModeIcon(
                    isSelected = !isAudioModeSelected,
                )

                AudioModeIcon(
                    isSelected = isAudioModeSelected,
                )
            }
        }
    }
}

@Composable
private fun TextModeIcon(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    SwitchIcon(
        modifier = modifier,
        isSelected = isSelected,
        image = Icons.AutoMirrored.Rounded.FormatAlignLeft,
    )
}

@Composable
private fun AudioModeIcon(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    SwitchIcon(
        modifier = modifier,
        isSelected = isSelected,
        image = Icons.Rounded.Headphones,
    )
}

@Composable
private fun SwitchIcon(
    isSelected: Boolean,
    image: ImageVector,
    modifier: Modifier = Modifier,
) {
    val color by animateColorAsState(
        label = "SwitchIconColorAnimation",
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        animationSpec = spring(stiffness = AnimationSpringStiffness),
    )

    Icon(
        modifier = modifier
            .size(IconSize)
            .padding(IconPadding),
        imageVector = image,
        contentDescription = null,
        tint = color,
    )
}

@Composable
private fun SelectionIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(IconSize)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPadded() {
    AudiobookPlayerTheme {
        var isAudioModeSelected by remember { mutableStateOf(true) }

        AudiobookPlayerModeSwitch(
            modifier = Modifier.padding(16.dp),
            isAudioModeSelected = isAudioModeSelected,
            onAudioModeSelectionChange = { isAudioModeSelected = it },
        )
    }
}
