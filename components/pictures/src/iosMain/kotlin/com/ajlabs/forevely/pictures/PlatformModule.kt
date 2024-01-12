package com.ajlabs.forevely.pictures

import co.touchlab.kermit.Logger.Companion.withTag
import org.koin.dsl.module

@Suppress("unused")
actual val picturesModule = module {
    single<MediaPickerController> {
        MediaPickerControllerIos(
            logger = withTag(MediaPickerController::class.simpleName!!),
            getViewController = get()
        )
    }
    single<PicturesService> {
        PicturesServiceIos(
            logger = withTag(PicturesService::class.simpleName!!),
            mediaPickerController = get()
        )
    }
}
