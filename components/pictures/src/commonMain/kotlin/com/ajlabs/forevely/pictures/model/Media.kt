package com.ajlabs.forevely.pictures.model

data class Media(
    val name: String,
    val path: String,
    val preview: Bitmap,
    val type: MediaType,
)
