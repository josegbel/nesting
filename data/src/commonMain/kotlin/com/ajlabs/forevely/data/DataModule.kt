package com.ajlabs.forevely.data

import co.touchlab.kermit.Logger.Companion.withTag
import com.ajlabs.forevely.data.apollo.apolloModule
import com.ajlabs.forevely.data.service.AuthService
import com.ajlabs.forevely.data.service.AuthServiceImpl
import com.ajlabs.forevely.data.service.ConversationService
import com.ajlabs.forevely.data.service.ConversationServiceImpl
import com.ajlabs.forevely.data.service.DebugService
import com.ajlabs.forevely.data.service.DebugServiceImpl
import com.ajlabs.forevely.data.service.MatcherService
import com.ajlabs.forevely.data.service.MatcherServiceImpl
import com.ajlabs.forevely.data.service.MessageService
import com.ajlabs.forevely.data.service.MessageServiceImpl
import com.ajlabs.forevely.data.service.UserService
import com.ajlabs.forevely.data.service.UserServiceImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(
        platformModule,
        apolloModule,
    )

    single<DebugService> {
        DebugServiceImpl(
            apolloClient = get(),
        )
    }
    single<AuthService> {
        AuthServiceImpl(
            logger = withTag(AuthService::class.simpleName!!),
            apolloClient = get(),
            settings = get(named(SettingsType.SETTINGS_ENCRYPTED.name)),
        )
    }
    single<UserService> {
        UserServiceImpl(
            apolloClient = get(),
        )
    }
    single<MessageService> {
        MessageServiceImpl(
            apolloClient = get(),
        )
    }
    single<MatcherService> {
        MatcherServiceImpl(
            apolloClient = get(),
        )
    }
    single<ConversationService> {
        ConversationServiceImpl(
            apolloClient = get(),
        )
    }
}

internal expect val platformModule: Module

internal enum class NormalizedCacheType { SQL, MEMORY, BOTH }

enum class SettingsType { SETTINGS_NON_ENCRYPTED, SETTINGS_ENCRYPTED }
