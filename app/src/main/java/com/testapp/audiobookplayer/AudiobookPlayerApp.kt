package com.testapp.audiobookplayer

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class AudiobookPlayerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AudiobookPlayerApp)
            modules(AudiobookPlayerModule().module)
        }
    }
}
