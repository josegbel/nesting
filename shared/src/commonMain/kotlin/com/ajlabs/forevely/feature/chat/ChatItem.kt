package com.ajlabs.forevely.feature.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.components.Thumbnail
import com.ajlabs.forevely.util.toDateOrTime

@Composable
internal fun ChatItem(
    modifier: Modifier = Modifier,
    previousMsg: ChatMessage?,
    currentMsg: ChatMessage,
    nextMsg: ChatMessage?,
    isFirstMessageOfDay: Boolean,
    isLastMatcherMsgRead: Boolean?,
    thumbUrl: String?,
) {
    val isSenderFirstMsg = previousMsg?.let { it.sender != currentMsg.sender } ?: true
    val isPreviousMsgRecent = previousMsg?.let {
        !isSenderFirstMsg && (it.timestamp + RECENT) > currentMsg.timestamp
    } ?: false

    val isSenderLastMsg = nextMsg?.let { it.sender != currentMsg.sender } ?: true
    val isNextMsgRecent = nextMsg?.let {
        !isSenderLastMsg && (nextMsg.timestamp - RECENT) < currentMsg.timestamp
    } ?: false

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (isFirstMessageOfDay) {
            Text(
                text = currentMsg.timestamp.toDateOrTime(),
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground,
                modifier = modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        ChatBubble(
            content = currentMsg.content,
            sender = currentMsg.sender,
            isPreviousMsgRecent = isPreviousMsgRecent,
            isNextMsgRecent = isNextMsgRecent,
            isSenderLastMsg = isSenderLastMsg,
            modifier = modifier,
        )
        Row {
            if (currentMsg.sender == Sender.USER) {
                Spacer(modifier = Modifier.weight(1f))
            }
            thumbUrl?.let { url ->
                AnimatedVisibility(
                    visible = isLastMatcherMsgRead == true,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Thumbnail(
                        url = url,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            if (currentMsg.sender == Sender.MATCHER) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ChatBubble(
    modifier: Modifier = Modifier,
    content: String,
    sender: Sender,
    isPreviousMsgRecent: Boolean,
    isNextMsgRecent: Boolean,
    isSenderLastMsg: Boolean,
) {
    val topStart by animateDpAsState(
        if (sender == Sender.MATCHER && isPreviousMsgRecent) 0.dp else 16.dp
    )
    val topEnd by animateDpAsState(
        if (sender == Sender.USER && isPreviousMsgRecent) 0.dp else 16.dp
    )
    val bottomStart by animateDpAsState(
        if (sender == Sender.MATCHER && isNextMsgRecent) 0.dp else 16.dp
    )
    val bottomEnd by animateDpAsState(
        if (sender == Sender.USER && isNextMsgRecent) 0.dp else 16.dp
    )

    val bgColorMsg = if (sender == Sender.USER) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.background
    }

    val textColorMsg = if (sender == Sender.USER) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onBackground
    }

    val bottomPadding = if (isSenderLastMsg) 8.dp else 0.dp

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = bottomPadding)
    ) {
        if (sender == Sender.USER) {
            Spacer(modifier = Modifier.weight(1f))
        }
        Surface(
            color = bgColorMsg,
            shape = RoundedCornerShape(
                topStart = topStart,
                topEnd = topEnd,
                bottomStart = bottomStart,
                bottomEnd = bottomEnd,
            ),
            modifier = modifier,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = 12.dp,
                    )
            ) {
                Text(
                    text = content,
                    color = textColorMsg,
                )
            }
        }
        if (sender == Sender.MATCHER) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
