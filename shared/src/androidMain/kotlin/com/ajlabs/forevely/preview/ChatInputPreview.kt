package com.ajlabs.forevely.preview

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ajlabs.forevely.feature.chat.ChatInputBar

@Preview(showBackground = true)
@Composable
private fun ChatInputLight() {
    MaterialTheme(lightColors()) {
        ChatInputBar(
            value = "Hello World",
            onValueChange = {},
            onSend = {},
            onAddAttachmentClick = {},
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatInputDark() {
    MaterialTheme(darkColors()) {
        ChatInputBar(
            value = "Hello World",
            onValueChange = {},
            onSend = {},
            onAddAttachmentClick = {},
            modifier = Modifier
        )
    }
}
