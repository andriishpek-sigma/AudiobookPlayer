package com.testapp.audiobookplayer

import org.koin.android.test.verify.androidVerify
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.junit.Test

class KoinModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `Check koin DI tree is valid`() {
        AudiobookPlayerModule().module.androidVerify()
    }
}
