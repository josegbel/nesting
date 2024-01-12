package com.ajlabs.forevely.feature.updateProfile

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

internal class UpdateProfileEventHandler(
    private val onError: suspend (String) -> Unit,
) : EventHandler<UpdateProfileContract.Inputs, UpdateProfileContract.Events, UpdateProfileContract.State> {
    override suspend fun EventHandlerScope<
        UpdateProfileContract.Inputs,
        UpdateProfileContract.Events,
        UpdateProfileContract.State,
        >.handleEvent(
        event: UpdateProfileContract.Events,
    ) = when (event) {
        is UpdateProfileContract.Events.OnError -> onError(event.message)
    }
}
