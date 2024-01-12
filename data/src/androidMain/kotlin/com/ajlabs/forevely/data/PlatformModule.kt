package com.ajlabs.forevely.data

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.apollographql.apollo3.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

@Suppress("unused")
internal actual val platformModule: Module = module {
//    factory {
//        HttpClient(CIO) {
//            engine {
//                https {
//                    trustManager = MyTrustManager(this)
//                }
//            }
//        }
//    }
    single<NormalizedCacheFactory>(named(NormalizedCacheType.SQL)) {
        SqlNormalizedCacheFactory(
            context = get(),
            name = BuildKonfig.dbName,
        )
    }
    single<Settings>(named(SettingsType.SETTINGS_NON_ENCRYPTED.name)) {
        SharedPreferencesSettings.Factory(get()).create("SETTINGS_NON_ENCRYPTED")
    }

    single<Settings>(named(SettingsType.SETTINGS_ENCRYPTED.name)) {
        val prefs = EncryptedSharedPreferences.create(
            get(),
            SettingsType.SETTINGS_ENCRYPTED.name,
            MasterKey.Builder(get())
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
        SharedPreferencesSettings(
            delegate = prefs,
        )
    }
}

//class MyTrustManager(private val config: TLSConfigBuilder) : X509TrustManager {
//    private val delegate = config.build().trustManager
//    private val extensions = X509TrustManagerExtensions(delegate)
//
//    override fun checkClientTrusted(certificates: Array<out X509Certificate>?, authType: String?) {}
//
//    override fun checkServerTrusted(certificates: Array<out X509Certificate>?, authType: String?) {}
//
//    override fun getAcceptedIssuers(): Array<X509Certificate> = delegate.acceptedIssuers
//}
