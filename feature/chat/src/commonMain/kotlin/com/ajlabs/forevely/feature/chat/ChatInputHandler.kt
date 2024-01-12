package com.ajlabs.forevely.feature.chat

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.filter
import com.ajlabs.forevely.data.service.ConversationService
import com.ajlabs.forevely.data.service.MessageService
import com.ajlabs.forevely.data.service.UserService
import com.ajlabs.forevely.data.type.SendMessageInput
import com.apollographql.apollo3.mpp.currentTimeMillis
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val PAGE_SIZE = 1
private typealias ChatInputScope =
    InputHandlerScope<ChatContract.Inputs, ChatContract.Events, ChatContract.State>

internal class ChatInputHandler(private val conversationId: String) : KoinComponent,
    InputHandler<ChatContract.Inputs, ChatContract.Events, ChatContract.State> {

    private val chatPagingSource = ChatPagingSource(conversationId)
    private val messageService: MessageService by inject()
    private val conversationService: ConversationService by inject()
    private val userService: UserService by inject()

    override suspend fun ChatInputScope.handleInput(
        input: ChatContract.Inputs,
    ) = when (input) {
        is ChatContract.Inputs.Init -> handleInit()
        ChatContract.Inputs.ObserveUserConversations -> observeUserConversations()
        is ChatContract.Inputs.SetIsLoading -> updateState { it.copy(isLoading = input.isLoading) }
        is ChatContract.Inputs.FetchMessagesPaged -> fetchMessagesPaged()
        is ChatContract.Inputs.SetMessagesLocal -> updateState { it.copy(messagesLocal = input.messagesLocal) }
        is ChatContract.Inputs.SetChatInput -> updateState { it.copy(textInput = input.text) }
        is ChatContract.Inputs.SendMessage -> sendMessage()
    }

    private suspend fun ChatInputScope.handleInit() {
        sideJob("handleInit") {
            postInput(ChatContract.Inputs.FetchMessagesPaged)
            postInput(ChatContract.Inputs.ObserveUserConversations)
        }
    }

    private suspend fun ChatInputScope.fetchMessagesPaged() {
        val messagesLocalIds = getCurrentState().messagesLocal.map { it.id }
        val pagingDataFlow = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = { chatPagingSource }
        )
            .flow
            .map { it.filter { it.id !in messagesLocalIds } }
        updateState { it.copy(messagesPagingDataFlow = pagingDataFlow) }
    }

    private suspend fun ChatInputScope.sendMessage() {
        val state = getCurrentState()
        val textInput = state.textInput.ifEmpty { return noOp() }

        sideJob("handleOnSendClicked") {
            val sendMessageInput = SendMessageInput(
                conversationId = conversationId,
                content = textInput,
            )
            messageService.sendMessage(sendMessageInput).fold(
                onSuccess = { data ->
                    val newChatMessage = ChatMessage(
                        id = data.sendMessage?.id.toString(),
                        content = textInput,
                        sender = Sender.USER,
                        timestamp = data.sendMessage?.timestamp?.toLong() ?: currentTimeMillis(),
                    )
                    val newMessages = listOf(newChatMessage) + state.messagesLocal
                    postInput(ChatContract.Inputs.SetMessagesLocal(newMessages))

                    postInput(ChatContract.Inputs.SetChatInput(""))
                },
                onFailure = {
                    postEvent(ChatContract.Events.OnError(it.message ?: "Error sending message"))
                }
            )
        }
    }

    private suspend fun ChatInputScope.observeUserConversations() {
        val state = getCurrentState()
        sideJob("watchConversations") {
            val user = userService.getUser().first().getOrNull()?.getUser ?: return@sideJob
            val userId = user.id.toString()
            conversationService.watchConversations(userId).collect { response ->

                logger.debug("Received: ${response.data?.watchConversations}")

                response.data?.watchConversations?.let { notification ->
                    val newChatMessage = ChatMessage(
                        id = notification.message.id.toString(),
                        content = notification.message.content,
                        sender = Sender.MATCHER,
                        timestamp = notification.message.timestamp.toLong(),
                    )
                    val newMessages = listOf(newChatMessage) + state.messagesLocal
                    postInput(ChatContract.Inputs.SetMessagesLocal(newMessages))
                }
            }
        }
    }
}
