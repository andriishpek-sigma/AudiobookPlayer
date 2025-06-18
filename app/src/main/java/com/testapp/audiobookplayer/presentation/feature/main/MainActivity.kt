package com.testapp.audiobookplayer.presentation.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.testapp.audiobookplayer.presentation.feature.audiobook.AudiobookPlayerScreen
import com.testapp.audiobookplayer.presentation.theme.AudiobookPlayerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AudiobookPlayerTheme {
                AudiobookPlayerScreen(
                    bookId = "1",
                )
            }
        }
    }
}
