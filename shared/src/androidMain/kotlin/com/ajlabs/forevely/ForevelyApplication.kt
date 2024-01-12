package com.ajlabs.forevely

import android.app.Application
import android.content.Context
import org.koin.dsl.module

class ForevelyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            additionalModules = listOf(
                module {
                    single<Context> { this@ForevelyApplication }
                },
            ),
        )
    }
}
