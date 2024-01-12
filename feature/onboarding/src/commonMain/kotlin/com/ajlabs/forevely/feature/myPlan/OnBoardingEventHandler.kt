package com.ajlabs.forevely.feature.myPlan

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

internal class OnBoardingEventHandler(
    private val onError: suspend (String) -> Unit,
) : EventHandler<OnBoardingContract.Inputs, OnBoardingContract.Events, OnBoardingContract.State> {
    override suspend fun EventHandlerScope<
        OnBoardingContract.Inputs,
        OnBoardingContract.Events,
        OnBoardingContract.State,
        >.handleEvent(
        event: OnBoardingContract.Events,
    ) = when (event) {
        is OnBoardingContract.Events.OnError -> onError(event.message)
    }
}
