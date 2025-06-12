package com.testapp.audiobookplayer

import com.testapp.audiobookplayer.data.DataModule
import com.testapp.audiobookplayer.domain.DomainModule
import com.testapp.audiobookplayer.presentation.PresentationModule
import org.koin.core.annotation.Module

@Module(includes = [PresentationModule::class, DomainModule::class, DataModule::class])
class AudiobookPlayerModule
