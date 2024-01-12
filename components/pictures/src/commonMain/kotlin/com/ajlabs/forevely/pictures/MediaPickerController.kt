package com.ajlabs.forevely.pictures

import com.ajlabs.forevely.pictures.model.Bitmap
import com.ajlabs.forevely.pictures.model.MediaSource

interface MediaPickerController {
    suspend fun pickImage(source: MediaSource): Bitmap?
}
