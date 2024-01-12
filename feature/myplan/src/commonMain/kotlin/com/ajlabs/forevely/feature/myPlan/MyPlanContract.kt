package com.ajlabs.forevely.feature.myPlan

import com.ajlabs.forevely.feature.myPlan.model.MyPlanModel
import org.koin.core.component.KoinComponent

object MyPlanContract : KoinComponent {
    data class State(
        val isLoading: Boolean = false,
        val myPlanModel: MyPlanModel? = null,
    )

    sealed interface Inputs {
        data class SetIsLoading(val isLoading: Boolean) : Inputs
        data object FetchMyPlanData : Inputs
        data class SetMyPlanModel(val myPlanModel: MyPlanModel) : Inputs
    }

    sealed interface Events {
        data class OnError(val message: String) : Events
    }
}
