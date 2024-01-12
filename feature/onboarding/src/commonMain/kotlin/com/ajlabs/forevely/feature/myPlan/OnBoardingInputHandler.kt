package com.ajlabs.forevely.feature.myPlan

import com.ajlabs.forevely.data.service.UserService
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private typealias OnBoardingInputScope =
    InputHandlerScope<OnBoardingContract.Inputs, OnBoardingContract.Events, OnBoardingContract.State>

internal class OnBoardingInputHandler :
    KoinComponent,
    InputHandler<OnBoardingContract.Inputs, OnBoardingContract.Events, OnBoardingContract.State> {

    private val userService by inject<UserService>()

    override suspend fun OnBoardingInputScope.handleInput(
        input: OnBoardingContract.Inputs,
    ) = when (input) {
        is OnBoardingContract.Inputs.SetIsLoading -> updateState { it.copy(isLoading = input.isLoading) }
        OnBoardingContract.Inputs.OnPhoneNumberSendClicked -> TODO()
        is OnBoardingContract.Inputs.SetPhoneNumber -> TODO()
    }
}
