package com.ajlabs.forevely.feature.myPlan

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

internal class MyPlanEventHandler(
    private val onError: suspend (String) -> Unit,
) : EventHandler<MyPlanContract.Inputs, MyPlanContract.Events, MyPlanContract.State> {
    override suspend fun EventHandlerScope<
        MyPlanContract.Inputs,
        MyPlanContract.Events,
        MyPlanContract.State,
        >.handleEvent(
        event: MyPlanContract.Events,
    ) = when (event) {
        is MyPlanContract.Events.OnError -> onError(event.message)
    }
}
