package com.ajlabs.forevely.feature.myPlan

import org.koin.core.component.KoinComponent

object OnBoardingContract : KoinComponent {
    data class State(
        val isLoading: Boolean = false,
        val phoneNumber: String = "",
    )

    sealed interface Inputs {
        data class SetIsLoading(val isLoading: Boolean) : Inputs
        data class SetPhoneNumber(val phoneNumber: String) : Inputs
        data object OnPhoneNumberSendClicked : Inputs
    }

    sealed interface Events {
        data class OnError(val message: String) : Events
    }
}
