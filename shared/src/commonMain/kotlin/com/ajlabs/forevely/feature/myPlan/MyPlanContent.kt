package com.ajlabs.forevely.feature.myPlan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.TopBar
import com.ajlabs.forevely.feature.myPlan.sections.PremiumSection
import com.ajlabs.forevely.feature.myPlan.sections.ProfileSection
import com.ajlabs.forevely.feature.myPlan.topbarActions.MyPlanTopBarEndAction
import com.ajlabs.forevely.feature.myPlan.topbarActions.MyPlanTopBarExtraEndAction
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString

@Composable
internal fun MyPlanContent(
    snackbarHostState: SnackbarHostState,
    onMenuClicked: () -> Unit,
    onExtraEndIconClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onLogOut: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val vm = remember(scope) {
        MyPlanViewModel(scope, onError = { snackbarHostState.showSnackbar(it) })
    }
    val state by vm.observeStates().collectAsState()
    var isMenuButtonVisible by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            state.myPlanModel?.let {
                ProfileSection(
                    userName = it.userName,
                    age = it.userAge,
                    imageUrl = it.imageUrl,
                    profileCompletion = it.profileCompletion,
                    completedProfileText = getString(Strings.MyPlan.ProfileCompleted),
                    notCompletedProfileText = getString(Strings.MyPlan.ProfileNotCompleted),
                    onCompleteProfileClick = onProfileClicked,
                )
            } ?: CircularProgressIndicator()

            PremiumSection()
        }
        TopBar(
            onMenuClicked = onMenuClicked,
            extraActionsStart = {},
            actionCenter = {},
            extraActionsEnd = {
                MyPlanTopBarExtraEndAction(
                    onClick = onExtraEndIconClicked,
                )
            },
            actionEnd = {
                MyPlanTopBarEndAction(
                    onClick = onSettingsClicked,
                )
            },
            isMenuButtonVisible = isMenuButtonVisible,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )
    }
}
