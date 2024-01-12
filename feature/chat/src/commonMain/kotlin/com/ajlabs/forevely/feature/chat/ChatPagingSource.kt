package com.ajlabs.forevely.feature.chat

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.ajlabs.forevely.data.service.AuthService
import com.ajlabs.forevely.data.service.MessageService
import com.ajlabs.forevely.data.type.PageInput
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal const val PAGE_SIZE_DEFAULT = 10

internal class ChatPagingSource(private val conversationId: String) : PagingSource<Int, ChatMessage>(),
    KoinComponent {
    private val messageService: MessageService by inject()
    private val authService: AuthService by inject()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatMessage> {
        val pageInput = PageInput(page = params.key ?: 0, size = PAGE_SIZE_DEFAULT)
        messageService.getMessagesPage(conversationId, pageInput).fold(
            onSuccess = { data ->
                val messages = data.getMessagesPage.messages
                println("messagesSize: ${messages.size}")

                val chatMessages = messages.map { message ->
                    val sender = if (message.senderId == authService.userId) Sender.USER else Sender.MATCHER

                    ChatMessage(
                        id = message.id.toString(),
                        content = message.content,
                        sender = sender,
                        timestamp = message.timestamp.toLong(),
                    )
                }
                return LoadResult.Page(
                    data = chatMessages,
                    prevKey = data.getMessagesPage.info.prev,
                    nextKey = data.getMessagesPage.info.next,
                )
            },
            onFailure = {
                return LoadResult.Error(it)
            },
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ChatMessage>): Int? {
        return state.anchorPosition
    }
}
