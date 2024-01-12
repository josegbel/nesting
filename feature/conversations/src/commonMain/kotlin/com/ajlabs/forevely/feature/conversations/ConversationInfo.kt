package com.ajlabs.forevely.feature.conversations

import kotlinx.serialization.Serializable

@Serializable
data class ConversationInfo(
    val id: String,
    val userPicture: String?,
    val matcherPicture: String?,
    val matcherName: String?,
)
