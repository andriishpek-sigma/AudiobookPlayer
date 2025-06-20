@file:OptIn(ExperimentalMaterial3Api::class)

package com.testapp.audiobookplayer.presentation.feature.audiobook.screen

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
import androidx.compose.runtime.remember
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
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val progress = if (duration == 0L) 0f else position.toFloat() / duration

    val interactionSource = remember { MutableInteractionSource() }

    Slider(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        value = progress,
        // TODO implement
        onValueChange = {},
        valueRange = 0f..1f,
        // TODO implement
        onValueChangeFinished = {},
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
        AudiobookPlayerProgressSlider(
            position = 2,
            duration = 10,
        )
    }
}
