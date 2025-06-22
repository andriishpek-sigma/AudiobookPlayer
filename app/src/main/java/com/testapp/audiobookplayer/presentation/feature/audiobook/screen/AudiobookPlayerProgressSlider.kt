@file:OptIn(ExperimentalMaterial3Api::class)

package com.testapp.audiobookplayer.presentation.feature.audiobook.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testapp.audiobookplayer.presentation.theme.AudiobookPlayerTheme

@Composable
fun AudiobookPlayerProgressSlider(
    position: Long,
    duration: Long,
    updatePosition: (Long) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val progress = if (duration == 0L) 0f else position.toFloat() / duration

    var uncommittedProgress by remember { mutableStateOf<Float?>(null) }
    var isUncommittedProgressNotified by remember { mutableStateOf(false) }

    val animatedCurrentProgress by animateFloatAsState(
        targetValue = uncommittedProgress ?: progress,
        animationSpec = if (uncommittedProgress != null) {
            snap()
        } else {
            spring(stiffness = Spring.StiffnessMedium)
        },
    )

    val interactionSource = remember { MutableInteractionSource() }

    // Reset uncommitted progress only when position update is completed
    LaunchedEffect(position) {
        if (!isUncommittedProgressNotified) {
            return@LaunchedEffect
        }

        uncommittedProgress = null
        isUncommittedProgressNotified = false
    }

    Slider(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        value = animatedCurrentProgress,
        onValueChange = {
            uncommittedProgress = it
            isUncommittedProgressNotified = false
        },
        valueRange = 0f..1f,
        onValueChangeFinished = {
            val lastUncommittedProgress = uncommittedProgress ?: return@Slider

            val newPosition = (lastUncommittedProgress * duration).toLong()
            if (position == newPosition) {
                uncommittedProgress = null
                return@Slider
            }

            isUncommittedProgressNotified = true
            updatePosition(newPosition)
        },
        interactionSource = interactionSource,
        thumb = { sliderState ->
            CustomThumb(
                interactionSource = interactionSource,
            )
        },
        track = { sliderState ->
            CustomTrack(
                state = sliderState,
            )
        },
    )
}

@Composable
private fun CustomThumb(
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier
            .size(16.dp)
            .hoverable(interactionSource = interactionSource)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ),
    )
}

@Composable
private fun CustomTrack(
    state: SliderState,
    modifier: Modifier = Modifier,
) {
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.secondaryContainer

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(CircleShape),
    ) {
        val thumbFraction = state.value
        val activeWidth = size.width * thumbFraction

        drawRect(
            color = activeColor,
            size = size.copy(width = activeWidth),
        )
        drawRect(
            color = inactiveColor,
            topLeft = Offset(x = activeWidth, y = 0f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AudiobookPlayerTheme {
        var position by remember { mutableLongStateOf(20) }

        AudiobookPlayerProgressSlider(
            position = position,
            duration = 100,
            updatePosition = {
                position = it
            },
        )
    }
}
