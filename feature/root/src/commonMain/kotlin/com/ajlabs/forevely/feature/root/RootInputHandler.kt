package com.ajlabs.forevely.feature.root

import com.ajlabs.forevely.data.service.AuthService
import com.ajlabs.forevely.data.service.ConversationService
import com.ajlabs.forevely.data.service.UserService
import com.ajlabs.forevely.notification.NotificationService
import com.ajlabs.forevely.notification.NotificationType
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private typealias RootInputScope =
    InputHandlerScope<RootContract.Inputs, RootContract.Events, RootContract.State>

internal class RootInputHandler :
    KoinComponent,
    InputHandler<RootContract.Inputs, RootContract.Events, RootContract.State> {

    private val authService: AuthService by inject()
    private val userService: UserService by inject()
    private val conversationService: ConversationService by inject()
    private val notificationService: NotificationService by inject()

    override suspend fun InputHandlerScope<RootContract.Inputs, RootContract.Events, RootContract.State>.handleInput(
        input: RootContract.Inputs,
    ) = when (input) {
        is RootContract.Inputs.Init -> handleInit()
        RootContract.Inputs.ObserveUser -> observeUser()
        RootContract.Inputs.ObserveAuthState -> observeAuthState()
        is RootContract.Inputs.StartObservingConversations -> observeConversations()
        RootContract.Inputs.StopObservingConversations -> cancelSideJob("observeConversations")
        is RootContract.Inputs.SendNotification -> sendNotification(input.title, input.body)
        is RootContract.Inputs.SetIsAuthenticated -> updateState { it.copy(isAuthenticated = input.isAuthenticated) }
        is RootContract.Inputs.SetIsLoading -> updateState { it.copy(isLoading = input.isLoading) }
        RootContract.Inputs.LogOut -> logOut()
    }

    private suspend fun RootInputScope.observeUser() {
        sideJob("observeUser") {
            userService.getUser()
                .distinctUntilChanged()
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            val conversationIds = it.getUser.conversationIds.map { it.toString() }
                            postInput(RootContract.Inputs.StartObservingConversations(conversationIds))
                        },
                        onFailure = {
                            postEvent(
                                RootContract.Events.OnError(
                                    it.message ?: "Error while getting user info"
                                )
                            )
                        }
                    )
                }
        }
    }

    private suspend fun RootInputScope.handleInit() {
        sideJob("rootHandleInit") {
            postInput(RootContract.Inputs.ObserveAuthState)
        }
    }

    private suspend fun RootInputScope.observeAuthState() {
        sideJob("observeAuth") {
            authService.observeToken()
                .map { !it.isNullOrEmpty() }
                .onEach { isAuthenticated ->
                    postInput(RootContract.Inputs.SetIsAuthenticated(isAuthenticated))

                    if (isAuthenticated) {
                        postInput(RootContract.Inputs.ObserveUser)
                    } else {
                        postInput(RootContract.Inputs.StopObservingConversations)
                    }
                }.collect()
        }
    }

    private suspend fun RootInputScope.observeConversations() {
        sideJob("watchConversations") {
            val userId = userService.getUser().first().getOrNull()?.getUser?.id?.toString() ?: return@sideJob
            conversationService.watchConversations(userId).collect { response ->
                response.data?.watchConversations?.let { notification ->
                    val senderName = notification.senderName ?: "Unknown"
                    val title = "New message from $senderName"
                    val body = notification.message.content
                    postInput(RootContract.Inputs.SendNotification(title, body))
                }
            }
        }
    }

    private suspend fun RootInputScope.sendNotification(title: String, body: String) {
        sideJob("sendNotification") {
            notificationService.schedule(
                notificationType = NotificationType.Immediate(
                    title = title,
                    body = body,
                )
            )
        }
    }

    private suspend fun RootInputScope.logOut() {
        sideJob("logOut") {
            authService.signOut()
        }
    }
}
