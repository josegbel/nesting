package com.ajlabs.forevely.feature.matcher.cards

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.dispatchers
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MatcherViewModel(
    scope: CoroutineScope,
    onError: suspend (String) -> Unit,
    showGetPremium: suspend () -> Unit,
) : BasicViewModel<
    MatcherContract.Inputs,
    MatcherContract.Events,
    MatcherContract.State,
    >(
    config = BallastViewModelConfiguration.Builder()
        .apply {
//            this += LoggingInterceptor()
            logger = { PrintlnLogger() }
        }
        .withViewModel(
            initialState = MatcherContract.State(),
            inputHandler = MatcherInputHandler(),
            name = tag,
        )
        .dispatchers(
            inputsDispatcher = Dispatchers.Main.immediate,
            eventsDispatcher = Dispatchers.Main.immediate,
            sideJobsDispatcher = Dispatchers.Default,
            interceptorDispatcher = Dispatchers.Default,
        )
        .build(),
    eventHandler = MatcherEventHandler(
        onError = onError,
        showGetPremium = showGetPremium,
    ),
    coroutineScope = scope,
) {
    init {
        trySend(MatcherContract.Inputs.Init)
    }

    companion object {
        private val tag = MatcherViewModel::class.simpleName!!
        internal val visibleItemIndexKey = tag + "visibleItemIndex"
        internal val progressKey: String = tag + "progress"
        internal val isUndoEnabledKey: String = tag + "isUndoEnabled"
        internal val availableUndo: String = tag + "availableUndo"
    }
}
