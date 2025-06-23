package com.testapp.audiobookplayer.presentation.util.media3

import android.content.ComponentName
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

@Composable
fun rememberMediaControllerStateWithLifecycle(
    isEnabled: Boolean = true,
    listener: (() -> MediaController.Listener)? = null,
    classProvider: () -> Class<*>,
): State<MediaController?> {
    val controllerState = remember { mutableStateOf<MediaController?>(null) }

    val context = LocalContext.current.applicationContext
    LifecycleStartEffect(isEnabled) {
        if (!isEnabled) {
            controllerState.value = null
            return@LifecycleStartEffect onStopOrDispose {}
        }

        val sessionToken = SessionToken(context, ComponentName(context, classProvider()))

        val controllerFuture = MediaController.Builder(context, sessionToken)
            .apply {
                listener?.let { setListener(it()) }
            }
            .buildAsync()

        controllerFuture.addListener(
            {
                if (controllerFuture.isDone) {
                    controllerState.value = controllerFuture.get()
                }
            },
            MoreExecutors.directExecutor(),
        )

        onStopOrDispose {
            MediaController.releaseFuture(controllerFuture)
            controllerState.value = null
        }
    }

    return controllerState
}
