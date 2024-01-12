package com.ajlabs.forevely.feature.conversations

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.dispatchers
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ConversationsViewModel(
    scope: CoroutineScope,
    onError: suspend (String) -> Unit,
    onConversationClicked: suspend (ConversationInfo) -> Unit,
) : BasicViewModel<
    ConversationsContract.Inputs,
    ConversationsContract.Events,
    ConversationsContract.State,
    >(
    config = BallastViewModelConfiguration.Builder()
        .apply {
//            this += LoggingInterceptor()
            logger = { PrintlnLogger() }
        }
        .withViewModel(
            initialState = ConversationsContract.State(),
            inputHandler = ConversationsInputHandler(),
            name = ConversationsViewModel::class.simpleName!!,
        )
        .dispatchers(
            inputsDispatcher = Dispatchers.Main.immediate,
            eventsDispatcher = Dispatchers.Main.immediate,
            sideJobsDispatcher = Dispatchers.Default,
            interceptorDispatcher = Dispatchers.Default,
        )
        .build(),
    eventHandler = ConversationsEventHandler(
        onError = onError,
        onConversationClicked = onConversationClicked,
    ),
    coroutineScope = scope,
) {
    init {
        trySend(ConversationsContract.Inputs.Init)
    }
}
