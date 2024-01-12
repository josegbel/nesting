package com.ajlabs.forevely.feature.matcher.cards

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

internal class MatcherEventHandler(
    private val onError: suspend (String) -> Unit,
    private val showGetPremium: suspend () -> Unit,
) : EventHandler<MatcherContract.Inputs, MatcherContract.Events, MatcherContract.State> {
    override suspend fun EventHandlerScope<
        MatcherContract.Inputs,
        MatcherContract.Events,
        MatcherContract.State,
        >.handleEvent(
        event: MatcherContract.Events,
    ) = when (event) {
        is MatcherContract.Events.OnError -> onError(event.message)
        MatcherContract.Events.ShowGetPremium -> showGetPremium()
    }
}

