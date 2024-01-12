package com.ajlabs.forevely.feature.conversations

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

internal class ConversationsEventHandler(
    private val onError: suspend (String) -> Unit,
    private val onConversationClicked: suspend (ConversationInfo) -> Unit,
) : EventHandler<ConversationsContract.Inputs, ConversationsContract.Events, ConversationsContract.State> {
    override suspend fun EventHandlerScope<ConversationsContract.Inputs, ConversationsContract.Events, ConversationsContract.State>.handleEvent(
        event: ConversationsContract.Events,
    ) = when (event) {
        is ConversationsContract.Events.OnError -> onError(event.message)
        is ConversationsContract.Events.OnConversationClicked -> onConversationClicked(event.conversationInfo)
    }
}
