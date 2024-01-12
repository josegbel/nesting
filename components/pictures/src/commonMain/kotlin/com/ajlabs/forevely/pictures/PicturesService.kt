package com.ajlabs.forevely.pictures

import com.ajlabs.forevely.core.models.PermissionStatus
import com.ajlabs.forevely.pictures.model.Bitmap
import com.ajlabs.forevely.pictures.model.MediaSource

interface PicturesService {
    fun checkGalleryPermission(): PermissionStatus
    fun checkCameraPermission(): PermissionStatus
    suspend fun requestGalleryPermission()
    suspend fun requestCameraPermission()
    fun openSettings()
    suspend fun pickImage(source: MediaSource): Bitmap?
}
