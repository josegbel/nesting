package com.ajlabs.forevely.feature.debug

import com.ajlabs.forevely.location.locationModule
import com.ajlabs.forevely.notification.notificationModule
import com.ajlabs.forevely.pictures.picturesModule
import org.koin.dsl.module

val debugModule = module {
    includes(
        locationModule,
        notificationModule,
        picturesModule,
    )
}
