package com.ajlabs.forevely.feature.myPlan

import com.ajlabs.forevely.data.service.UserService
import com.ajlabs.forevely.feature.myPlan.model.MyPlanModel
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private typealias MyPlanInputScope =
    InputHandlerScope<MyPlanContract.Inputs, MyPlanContract.Events, MyPlanContract.State>

internal class MyPlanInputHandler :
    KoinComponent,
    InputHandler<MyPlanContract.Inputs, MyPlanContract.Events, MyPlanContract.State> {

    private val userService by inject<UserService>()

    override suspend fun MyPlanInputScope.handleInput(
        input: MyPlanContract.Inputs,
    ) = when (input) {
        is MyPlanContract.Inputs.SetIsLoading -> updateState { it.copy(isLoading = input.isLoading) }
        MyPlanContract.Inputs.FetchMyPlanData -> fetchMyPlanData()
        is MyPlanContract.Inputs.SetMyPlanModel -> updateState { it.copy(myPlanModel = input.myPlanModel) }
    }

    private suspend fun MyPlanInputScope.fetchMyPlanData() {
        sideJob("fetchMyPlanData") {
            userService.getMyPlan().fold(
                onSuccess = {
                    val user = it.getUser
                    val myPlanModel = MyPlanModel(
                        userName = user.details.name,
                        userAge = user.details.age,
                        imageUrl = user.profile.pictures.firstOrNull(),
                        profileCompletion = user.profileCompletion.toInt(),
                        isPremium = user.isPremium,
                    )
                    postInput(MyPlanContract.Inputs.SetMyPlanModel(myPlanModel))
                },
                onFailure = {
                    postEvent(MyPlanContract.Events.OnError(it.message ?: "Error while getting user"))
                },
            )
        }
    }
}
