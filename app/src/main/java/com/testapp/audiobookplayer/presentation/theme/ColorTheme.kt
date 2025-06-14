package com.testapp.audiobookplayer.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AudiobookColorScheme(
    val secondaryContent: Color,
    val material: ColorScheme,
) {

    companion object {

        fun resolve(isDarkTheme: Boolean): AudiobookColorScheme {
            return if (isDarkTheme) DarkColorScheme else LightColorScheme
        }
    }
}

@SuppressLint("ComposeCompositionLocalUsage")
val LocalAppColorScheme = staticCompositionLocalOf<AudiobookColorScheme> {
    error("App color scheme missing")
}

private val DarkColorScheme = AudiobookColorScheme(
    secondaryContent = ColorPalette.GreyEvenMoreDarker,
    material = darkColorScheme(
        primary = ColorPalette.BlueLighter,
        onPrimary = Color.White,
//    primaryContainer = ,
//    onPrimaryContainer = ,
//    inversePrimary = ,
        secondary = ColorPalette.BlueLighter,
        onSecondary = Color.White,
        secondaryContainer = ColorPalette.GreySuperDark,
        onSecondaryContainer = Color.White,
        tertiary = ColorPalette.BlueLighter,
        onTertiary = Color.White,
//    tertiaryContainer = ,
//    onTertiaryContainer = ,
        background = ColorPalette.GreyAlmostBlack,
        onBackground = Color.White,
        surface = ColorPalette.GreyExtraDark,
        onSurface = Color.White,
//    surfaceVariant =,
//    onSurfaceVariant =,
//    surfaceTint =,
//    inverseSurface =,
//    inverseOnSurface =,
//    error =,
//    onError =,
//    errorContainer =,
//    onErrorContainer =,
        outline = ColorPalette.GreyBeforeSuperDarker,
        outlineVariant = ColorPalette.GreyBeforeSuperDarker,
//    scrim =,
//    surfaceBright =,
//    surfaceContainer =,
        surfaceContainerHigh = ColorPalette.GreyExtraDark,
//    surfaceContainerHighest =,
//    surfaceContainerLow =,
//    surfaceContainerLowest =,
//    surfaceDim =,
    ),
)

private val LightColorScheme = AudiobookColorScheme(
    secondaryContent = ColorPalette.GreyDarker,
    material = lightColorScheme(
        primary = ColorPalette.BlueDarker,
        onPrimary = Color.White,
//    primaryContainer = ,
//    onPrimaryContainer = ,
//    inversePrimary = ,
        secondary = ColorPalette.BlueDarker,
        onSecondary = Color.White,
        secondaryContainer = ColorPalette.GreyLighter,
        onSecondaryContainer = Color.Black,
        tertiary = ColorPalette.BlueDarker,
        onTertiary = Color.White,
//    tertiaryContainer = ,
//    onTertiaryContainer = ,
        background = ColorPalette.YellowLight,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
//    surfaceVariant =,
//    onSurfaceVariant =,
//    surfaceTint =,
//    inverseSurface =,
//    inverseOnSurface =,
//    error =,
//    onError =,
//    errorContainer =,
//    onErrorContainer =,
        outline = ColorPalette.GreyEvenMoreLighter,
        outlineVariant = ColorPalette.GreyEvenMoreLighter,
//    scrim =,
//    surfaceBright =,
//    surfaceContainer =,
        surfaceContainerHigh = ColorPalette.GreySuperLight,
//    surfaceContainerHighest =,
//    surfaceContainerLow =,
//    surfaceContainerLowest =,
//    surfaceDim =,
    ),
)
