package com.ajlabs.forevely.preview

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ajlabs.forevely.feature.chat.ChatList
import com.ajlabs.forevely.feature.chat.ChatMessage
import com.ajlabs.forevely.feature.chat.RECENT
import com.ajlabs.forevely.feature.chat.Sender
import kotlinx.datetime.Clock

private fun messages() = listOf(
    ChatMessage(
        id = "1",
        content = "Hi",
        sender = Sender.USER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 45_000 - RECENT,
    ),
    ChatMessage(
        id = "2",
        content = "Forgot to ask you...",
        sender = Sender.USER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 25_000,
    ),
    ChatMessage(
        id = "3",
        content = "How are you?",
        sender = Sender.USER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 12_000,
    ),
    ChatMessage(
        id = "4",
        content = "I'm good, thanks",
        sender = Sender.MATCHER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 11_000,
    ),
    ChatMessage(
        id = "4",
        content = "Missed your previous message, sorry...Missed your previous message, sorry...",
        sender = Sender.MATCHER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 11_500,
    ),
    ChatMessage(
        id = "5",
        content = "Are you there?",
        sender = Sender.MATCHER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 7_000 - RECENT,
    ),
    ChatMessage(
        id = "5",
        content = "How about you?",
        sender = Sender.MATCHER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 6_000,
    ),
    ChatMessage(
        id = "6",
        content = "I'm good too",
        sender = Sender.USER,
        timestamp = Clock.System.now().toEpochMilliseconds() - 5_000,
    ),
    ChatMessage(
        id = "7",
        content = "I'm working on a new project",
        sender = Sender.MATCHER,
        timestamp = Clock.System.now().toEpochMilliseconds(),
    ),
).reversed()

@Composable
private fun Chat() {
    ChatList(
        modifier = Modifier,
        lazyListState = rememberLazyListState(),
        messagesLocal = messages(),
        messagesPaged = null,
        isLoadingPaging = false,
        thumbUrl = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun ChatListLightSingle() {
    PreviewLayout {
        Chat()
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListDarkSingle() {
    PreviewLayout(darkTheme = true) {
        Chat()
    }
}
