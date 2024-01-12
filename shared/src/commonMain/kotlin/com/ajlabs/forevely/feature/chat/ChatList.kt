package com.ajlabs.forevely.feature.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import com.ajlabs.forevely.components.Loader
import com.ajlabs.forevely.util.date

internal const val RECENT = 50_000

@Composable
internal fun ChatList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    messagesPaged: LazyPagingItems<ChatMessage>?,
    messagesLocal: List<ChatMessage>,
    isLoadingPaging: Boolean,
    thumbUrl: String?,
) {
    LazyColumn(
        state = lazyListState,
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(
            top = 72.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 72.dp,
        ),
        modifier = modifier
    ) {
        items(messagesLocal.count()) { index ->
            val previousMsg = messagesLocal.getOrNull(index + 1) // + instead of - because list is reversed
            val currentMsg = messagesLocal[index]
            val nextMsg = messagesLocal.getOrNull(index - 1) // - instead of + because list is reversed

            val messagesGroupedByDays = messagesLocal.groupBy { it.timestamp.date() }

            val isFirstMessageOfDay = messagesGroupedByDays[currentMsg.timestamp.date()]
                ?.lastOrNull()?.id == currentMsg.id // Using last because list is reversed

            val isLastMatcherMsgRead = messagesLocal
                .firstOrNull { it.sender == Sender.USER }
                ?.let { it.id == currentMsg.id } ?: false

            ChatItem(
                previousMsg = previousMsg,
                currentMsg = currentMsg,
                nextMsg = nextMsg,
                thumbUrl = thumbUrl,
                isFirstMessageOfDay = isFirstMessageOfDay,
                isLastMatcherMsgRead = isLastMatcherMsgRead
            )
        }
        messagesPaged?.let { messages ->
            items(messages.itemCount) { index ->
                messages[index]?.let { message ->
                    val previousMsg = try {
                        messages.get(index + 1) // + instead of - because list is reversed
                    } catch (e: IndexOutOfBoundsException) {
                        null
                    }
                    val nextMsg = try {
                        messages[index - 1] // - instead of + because list is reversed
                    } catch (e: IndexOutOfBoundsException) {
                        null
                    }

                    val messagesGroupedByDays = messages.itemSnapshotList
                        .groupBy { it?.timestamp?.date() }
                        .filterNot { it.key == null }

                    val isFirstMessageOfDay = messagesGroupedByDays[message.timestamp.date()]
                        ?.lastOrNull()?.id == message.id // Using last because list is reversed

                    val isLastMatcherMsgRead = messagesLocal
                        .firstOrNull { it.sender == Sender.USER }
                        ?.let { it.id == message.id && messagesLocal.none { it.sender == Sender.USER } }
                        ?: false

                    ChatItem(
                        previousMsg = previousMsg,
                        currentMsg = message,
                        nextMsg = nextMsg,
                        thumbUrl = thumbUrl,
                        isFirstMessageOfDay = isFirstMessageOfDay,
                        isLastMatcherMsgRead = isLastMatcherMsgRead
                    )
                }
            }
        }
        if (isLoadingPaging) {
            item {
                Loader()
            }
        }
        item {
            AnimatedVisibility(
                visible = messagesLocal.isEmpty() &&
                    messagesPaged?.itemSnapshotList?.isEmpty() == true &&
                    !isLoadingPaging,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = "No messages yet",
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}
