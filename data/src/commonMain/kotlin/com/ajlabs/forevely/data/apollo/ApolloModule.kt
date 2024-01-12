package com.ajlabs.forevely.data.apollo

import co.touchlab.kermit.Logger.Companion.withTag
import com.ajlabs.forevely.data.BuildKonfig
import com.ajlabs.forevely.data.NormalizedCacheType
import com.ajlabs.forevely.data.SettingsType
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.api.NormalizedCacheFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val apolloModule = module {
    single<NormalizedCacheFactory>(named(NormalizedCacheType.MEMORY)) {
        MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
    }
    single<NormalizedCacheFactory>(named(NormalizedCacheType.BOTH)) {
        val memoryCache = get<NormalizedCacheFactory>(named(NormalizedCacheType.MEMORY))
        val sqlCache = get<NormalizedCacheFactory>(named(NormalizedCacheType.SQL))
        memoryCache.chain(sqlCache)
    }

    single {
        AuthorizationInterceptor(
            logger = withTag(AuthorizationInterceptor::class.simpleName!!),
            settings = get(named(SettingsType.SETTINGS_ENCRYPTED.name)),
        )
    }
    single<ApolloClient> {
        ApolloProviderImpl(
            baseUrlGraphQl = BuildKonfig.serverUrlGraphQl,
            baseUrlSubscriptions = BuildKonfig.serverUrlSubscriptions,
            authorizationInterceptor = get(),
            normalizedCacheFactory = get(named(NormalizedCacheType.BOTH)),
            dispatcher = Dispatchers.IO,
        ).provide()
    }
}
