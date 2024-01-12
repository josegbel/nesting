package com.ajlabs.forevely.feature.myPlan

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class MyPlanViewModel(
    scope: CoroutineScope,
    onError: suspend (String) -> Unit,
) : BasicViewModel<
    MyPlanContract.Inputs,
    MyPlanContract.Events,
    MyPlanContract.State,
    >(
    config = BallastViewModelConfiguration.Builder()
        .apply {
//            this += LoggingInterceptor()
            logger = { PrintlnLogger() }
        }
        .withViewModel(
            initialState = MyPlanContract.State(),
            inputHandler = MyPlanInputHandler(),
            name = tag,
        )
        .build(),
    eventHandler = MyPlanEventHandler(
        onError = onError,
    ),
    coroutineScope = scope,
) {
    init {
        trySend(MyPlanContract.Inputs.FetchMyPlanData)
    }

    companion object {
        private val tag = MyPlanViewModel::class.simpleName!!
    }
}
