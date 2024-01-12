package com.ajlabs.forevely.feature.root

import com.ajlabs.forevely.data.service.AuthService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RootContract : KoinComponent {
    private val authService: AuthService by inject()

    data class State(
        val isLoading: Boolean = false,
        val isAuthenticated: Boolean = authService.checkAuth(),
    )

    sealed interface Inputs {
        data class SetIsLoading(val isLoading: Boolean) : Inputs

        data object Init : Inputs
        data object ObserveAuthState : Inputs
        data object ObserveUser : Inputs
        data class StartObservingConversations(val conversationIds: List<String>) : Inputs
        data object StopObservingConversations : Inputs
        data class SendNotification(val title: String, val body: String) : Inputs
        data object LogOut : Inputs

        data class SetIsAuthenticated(val isAuthenticated: Boolean) : Inputs
    }

    sealed interface Events {
        data class OnError(val message: String) : Events
    }
}
