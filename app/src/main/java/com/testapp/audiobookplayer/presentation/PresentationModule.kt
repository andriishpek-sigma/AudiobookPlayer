package com.testapp.audiobookplayer.presentation

import com.testapp.audiobookplayer.domain.DomainModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DomainModule::class])
@ComponentScan
class PresentationModule
