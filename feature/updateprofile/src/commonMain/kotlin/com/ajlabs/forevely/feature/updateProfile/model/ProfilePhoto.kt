package com.ajlabs.forevely.feature.updateProfile.model

import com.ajlabs.forevely.pictures.model.Bitmap

data class ProfilePhoto(
    // commenting url for now as photos are not coming from network
//    val url: String? = null,
    val bitmap: Bitmap? = null,
)
