package com.testapp.audiobookplayer.app

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import kotlinx.coroutines.Dispatchers

@Module
class DispatchersModule {

    @Factory
    @DispatcherType.IO
    fun provideIoDispatcher() = Dispatchers.IO

    @Factory
    @DispatcherType.Default
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Factory
    @DispatcherType.Main
    fun provideMainDispatcher() = Dispatchers.Main
}

object DispatcherType {

    @Named
    annotation class IO

    @Named
    annotation class Default

    @Named
    annotation class Main
}
