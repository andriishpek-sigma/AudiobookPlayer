package com.testapp.audiobookplayer.presentation.feature.player

import android.app.PendingIntent
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.testapp.audiobookplayer.presentation.feature.main.MainActivity

class AudiobookPlayerService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    // Create your player and media session in the onCreate lifecycle event
    override fun onCreate() {
        super.onCreate()

        val basePlayer = createExoPlayerInstance()
        val audiobookPlayer = AudiobookPlayer(basePlayer)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        mediaSession = MediaSession.Builder(this, audiobookPlayer)
            .setSessionActivity(pendingIntent)
            .build()
    }

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    @OptIn(UnstableApi::class)
    private fun createExoPlayerInstance(): ExoPlayer =
        ExoPlayer.Builder(this)
            .setWakeMode(C.WAKE_MODE_NETWORK)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(10000)
            .setMaxSeekToPreviousPositionMs(Long.MAX_VALUE)
            .setHandleAudioBecomingNoisy(true)
            .build()
            .apply {
                repeatMode = Player.REPEAT_MODE_OFF
            }
}
