package com.ajlabs.forevely.feature.myPlan

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class OnBoardingViewModel(
    scope: CoroutineScope,
    onError: suspend (String) -> Unit,
) : BasicViewModel<
    OnBoardingContract.Inputs,
    OnBoardingContract.Events,
    OnBoardingContract.State,
    >(
    config = BallastViewModelConfiguration.Builder()
        .apply {
//            this += LoggingInterceptor()
            logger = { PrintlnLogger() }
        }
        .withViewModel(
            initialState = OnBoardingContract.State(),
            inputHandler = OnBoardingInputHandler(),
            name = OnBoardingViewModel::class.simpleName!!,
        )
        .build(),
    eventHandler = OnBoardingEventHandler(
        onError = onError,
    ),
    coroutineScope = scope,
)
