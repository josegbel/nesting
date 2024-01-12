package com.ajlabs.forevely.pictures

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import co.touchlab.kermit.Logger
import com.ajlabs.forevely.core.models.Permission
import com.ajlabs.forevely.core.models.PermissionStatus
import com.ajlabs.forevely.core.util.checkPermissions
import com.ajlabs.forevely.core.util.openAppSettingsPage
import com.ajlabs.forevely.core.util.providePermissions
import com.ajlabs.forevely.pictures.model.Bitmap
import com.ajlabs.forevely.pictures.model.MediaSource

class PicturesServiceAndroid(
    private val logger: Logger,
    private val context: Context,
    private val activity: Lazy<Activity>,
    private val mediaPickerController: MediaPickerController,
    private val cameraPermissionResultLauncher: ActivityResultLauncher<String>,
) : PicturesService {
    override fun checkGalleryPermission(): PermissionStatus {
        return checkPermissions(
            context = context,
            activity = activity,
            logger = logger,
            permissions = galleryCompat()
        )
    }

    override fun checkCameraPermission(): PermissionStatus {
        return checkPermissions(
            context = context,
            activity = activity,
            logger = logger,
            permissions = listOf(Manifest.permission.CAMERA)
        )
    }

    override suspend fun requestGalleryPermission() {
        activity.value.providePermissions(galleryCompat()) {
            error(it.localizedMessage ?: "Failed to request Gallery permission")
        }
    }

    override suspend fun requestCameraPermission() {
        activity.value.apply {
            cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun openSettings() {
        context.openAppSettingsPage(Permission.GALLERY)
    }

    override suspend fun pickImage(source: MediaSource): Bitmap? {
        logger.d { "Picking image from $source." }
        return mediaPickerController.pickImage(source)
    }
}

private fun galleryCompat() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    } else {
        listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
