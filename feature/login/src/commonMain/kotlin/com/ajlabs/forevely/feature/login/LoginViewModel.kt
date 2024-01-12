package com.ajlabs.forevely.feature.login

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.dispatchers
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginViewModel(
    scope: CoroutineScope,
    onError: suspend (String) -> Unit,
    onAuthenticated: () -> Unit,
) : BasicViewModel<
    LoginContract.Inputs,
    LoginContract.Events,
    LoginContract.State,
    >(
    config = BallastViewModelConfiguration.Builder()
        .apply {
//            this += LoggingInterceptor()
            logger = { PrintlnLogger() }
        }
        .withViewModel(
            initialState = LoginContract.State(),
            inputHandler = LoginInputHandler(),
            name = TAG,
        )
        .dispatchers(
            inputsDispatcher = Dispatchers.Main.immediate,
            eventsDispatcher = Dispatchers.Main.immediate,
            sideJobsDispatcher = Dispatchers.Default,
            interceptorDispatcher = Dispatchers.Default,
        )
        .build(),
    eventHandler = LoginEventHandler(
        onError = onError,
        onAuthenticated = onAuthenticated,
    ),
    coroutineScope = scope,
) {
    companion object {
        private val TAG = LoginViewModel::class.simpleName!!
    }
}
