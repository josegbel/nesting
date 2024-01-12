package com.ajlabs.forevely.feature.chat

data class ChatMessage(
    val id: String,
    val content: String,
    val sender: Sender,
    val timestamp: Long,
)

enum class Sender {
    USER,
    MATCHER,
}
