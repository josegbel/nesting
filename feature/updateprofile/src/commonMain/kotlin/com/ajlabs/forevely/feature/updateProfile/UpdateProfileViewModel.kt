package com.ajlabs.forevely.feature.updateProfile

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.dispatchers
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class UpdateProfileViewModel(
    scope: CoroutineScope,
    onError: suspend (String) -> Unit,
) : BasicViewModel<
    UpdateProfileContract.Inputs,
    UpdateProfileContract.Events,
    UpdateProfileContract.State,
    >(
    config = BallastViewModelConfiguration.Builder()
        .apply {
            this += LoggingInterceptor()
            logger = { PrintlnLogger() }
        }
        .withViewModel(
            initialState = UpdateProfileContract.State(),
            inputHandler = UpdateProfileInputHandler(),
            name = tag,
        )
        .dispatchers(
            inputsDispatcher = Dispatchers.Main.immediate,
            eventsDispatcher = Dispatchers.Main.immediate,
            sideJobsDispatcher = Dispatchers.Default,
            interceptorDispatcher = Dispatchers.Default,
        )
        .build(),
    eventHandler = UpdateProfileEventHandler(
        onError = onError,
    ),
    coroutineScope = scope,
) {

    init {
        trySend(UpdateProfileContract.Inputs.FetchUser)
        trySend(UpdateProfileContract.Inputs.ObserveCameraPermission)
        trySend(UpdateProfileContract.Inputs.ObserveGalleryPermission)
    }

    companion object {
        private val tag = UpdateProfileViewModel::class.simpleName!!
    }
}
