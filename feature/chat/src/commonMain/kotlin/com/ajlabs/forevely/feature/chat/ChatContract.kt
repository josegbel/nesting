package com.ajlabs.forevely.feature.chat

import app.cash.paging.PagingData
import com.ajlabs.forevely.data.GetMessagesPageQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object ChatContract {
    data class State(
        val isLoading: Boolean = false,

        val userPicture: String? = null,
        val matcherPicture: String? = null,
        val messagesPagingDataFlow: Flow<PagingData<ChatMessage>> = emptyFlow(),

        val messagesLocal: List<ChatMessage> = emptyList(),

        val isAwaitingAnswer: Boolean = false,
        val isAnswering: Boolean = false,
        val textInput: String = "",

        val pageInfo: GetMessagesPageQuery.Info? = null,
    )

    sealed interface Inputs {
        data object Init : Inputs
        data class SetIsLoading(val isLoading: Boolean) : Inputs
        data object ObserveUserConversations : Inputs
        data object FetchMessagesPaged : Inputs
        data class SetMessagesLocal(val messagesLocal: List<ChatMessage>) : Inputs
        data class SetChatInput(val text: String) : Inputs
        data object SendMessage : Inputs
    }

    sealed interface Events {
        data class OnError(val message: String) : Events
    }
}
