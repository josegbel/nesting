package com.ajlabs.forevely.feature.chat

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

internal class ChatEventHandler(
    private val onError: suspend (String) -> Unit,
) : EventHandler<ChatContract.Inputs, ChatContract.Events, ChatContract.State> {
    override suspend fun EventHandlerScope<ChatContract.Inputs, ChatContract.Events, ChatContract.State>.handleEvent(
        event: ChatContract.Events,
    ) = when (event) {
        is ChatContract.Events.OnError -> onError(event.message)
    }
}
