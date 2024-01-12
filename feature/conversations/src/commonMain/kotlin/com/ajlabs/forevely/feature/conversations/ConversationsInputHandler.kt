package com.ajlabs.forevely.feature.conversations

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import com.ajlabs.forevely.data.GetConversationsPageQuery
import com.ajlabs.forevely.data.GetMatchedUsersWithoutConversationQuery
import com.ajlabs.forevely.data.service.ConversationService
import com.ajlabs.forevely.data.service.UserService
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val PAGE_SIZE = 10
private typealias ConversationsInputScope =
    InputHandlerScope<ConversationsContract.Inputs, ConversationsContract.Events, ConversationsContract.State>

internal class ConversationsInputHandler :
    KoinComponent,
    InputHandler<ConversationsContract.Inputs, ConversationsContract.Events, ConversationsContract.State> {

    private val conversationService: ConversationService by inject()
    private val userService: UserService by inject()
    private val conversationsPagingSource = ConversationsPagingSource()
    private val matchersPagingSource = MatchersPagingSource()

    override suspend fun ConversationsInputScope.handleInput(
        input: ConversationsContract.Inputs,
    ) = when (input) {
        ConversationsContract.Inputs.Init -> initConversations()
        ConversationsContract.Inputs.FetchMatchers -> fetchMatchers()
        ConversationsContract.Inputs.FetchConversations -> fetchConversations()
        is ConversationsContract.Inputs.OnMatcherClicked -> onMatcherClicked(input.matcher)
        is ConversationsContract.Inputs.CreateConversation -> createConversation(input.matcherId, input.matcherPicture)
        is ConversationsContract.Inputs.OnConversationClicked -> onConversationClicked(input.conversation)

        ConversationsContract.Inputs.GetUserInfo -> getUserInfo()
        is ConversationsContract.Inputs.SetUserPicture -> updateState { it.copy(userPicture = input.userPicture) }
    }

    private suspend fun ConversationsInputScope.getUserInfo() {
        sideJob("getUserInfo") {
            userService.getUser().first().fold(
                onSuccess = { data ->
                    val user = data.getUser
                    user.profile.pictures.firstOrNull()?.let { picture ->
                        postInput(ConversationsContract.Inputs.SetUserPicture(picture))
                    }
                },
                onFailure = {
                    postEvent(ConversationsContract.Events.OnError(it.message ?: "Error while getting user info"))
                }
            )
        }
    }

    private suspend fun ConversationsInputScope.onConversationClicked(conversation: GetConversationsPageQuery.Conversation) {
        val state = getCurrentState()
        sideJob("onConversationClicked") {
            // TODO: Record click in analytics
            val conversationInfo = ConversationInfo(
                id = conversation.id.toString(),
                userPicture = state.userPicture,
                matcherPicture = conversation.picture,
                matcherName = conversation.matcherName,
            )
            postEvent(ConversationsContract.Events.OnConversationClicked(conversationInfo))
        }
    }

    private suspend fun ConversationsInputScope.initConversations() {
        sideJob("initConversations") {
            postInput(ConversationsContract.Inputs.FetchConversations)
            postInput(ConversationsContract.Inputs.FetchMatchers)
        }
    }

    private suspend fun ConversationsInputScope.fetchMatchers() {
        val pagingDataFlow = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = { matchersPagingSource }
        ).flow
        updateState { it.copy(matchersPagingDataFlow = pagingDataFlow) }
    }

    private suspend fun ConversationsInputScope.fetchConversations() {
        val pagingDataFlow = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = { conversationsPagingSource }
        ).flow
        updateState { it.copy(conversationsPagingDataFlow = pagingDataFlow) }
    }

    private suspend fun ConversationsInputScope.onMatcherClicked(matcher: GetMatchedUsersWithoutConversationQuery.Matcher) {
        sideJob("onMatcherClicked") {
            // TODO: Record click in analytics
            postInput(ConversationsContract.Inputs.CreateConversation(matcher.id.toString(), matcher.picture))
        }
    }

    private suspend fun ConversationsInputScope.createConversation(matcherId: String, matcherPicture: String?) {
        val state = getCurrentState()
        sideJob("createConversation") {
            conversationService.createConversation(matcherId).fold(
                onSuccess = { data ->
                    postInput(ConversationsContract.Inputs.FetchMatchers)
                    postInput(ConversationsContract.Inputs.FetchConversations)

                    val conversationInfo = ConversationInfo(
                        id = data.createConversation.id.toString(),
                        userPicture = state.userPicture,
                        matcherPicture = matcherPicture,
                        matcherName = "TODO: add matcherName",
                    )
                    postEvent(ConversationsContract.Events.OnConversationClicked(conversationInfo))
                },
                onFailure = {
                    postEvent(ConversationsContract.Events.OnError(it.message ?: "Error while creating conversation"))
                }
            )
        }
    }
}
