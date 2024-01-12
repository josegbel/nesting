package com.ajlabs.forevely.feature.chat

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.dispatchers
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ChatViewModel(
    conversationId: String,
    scope: CoroutineScope,
    onError: suspend (String) -> Unit,
) : BasicViewModel<
    ChatContract.Inputs,
    ChatContract.Events,
    ChatContract.State,
    >(
    config = BallastViewModelConfiguration.Builder()
        .apply {
//            this += LoggingInterceptor()
            logger = { PrintlnLogger() }
        }
        .withViewModel(
            initialState = ChatContract.State(),
            inputHandler = ChatInputHandler(conversationId),
            name = ChatViewModel::class.simpleName!!,
        )
        .dispatchers(
            inputsDispatcher = Dispatchers.Main.immediate,
            eventsDispatcher = Dispatchers.Main.immediate,
            sideJobsDispatcher = Dispatchers.Default,
            interceptorDispatcher = Dispatchers.Default,
        )
        .build(),
    eventHandler = ChatEventHandler(
        onError = onError,
    ),
    coroutineScope = scope,
) {
    init {
        trySend(ChatContract.Inputs.Init)
    }
}
