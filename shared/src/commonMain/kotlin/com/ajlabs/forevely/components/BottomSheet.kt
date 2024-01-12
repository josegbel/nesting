package com.ajlabs.forevely.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ajlabs.forevely.feature.matcher.cards.AboutMeAttr

private val animationDuration = 300

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    modalSheet: ModalSheet,
    showSheet: Boolean,
    containerColor: Color = MaterialTheme.colors.primary,
    content: @Composable ColumnScope.() -> Unit,
) {
    val bgColor by animateColorAsState(
        targetValue = if (showSheet) Color.Black.copy(alpha = .5f) else Color.Transparent,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing,
        ),
    )

    fun <T> animationSpec() = tween<T>(
        durationMillis = animationDuration,
        easing = FastOutSlowInEasing,
    )

    val enter = when (modalSheet) {
        ModalSheet.MenuDrawer -> slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = animationSpec(),
        )

        else -> slideInVertically(
            initialOffsetY = { it },
            animationSpec = animationSpec(),
        )
    }

    val exit = when (modalSheet) {
        ModalSheet.MenuDrawer -> slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = animationSpec(),
        )


        else -> slideOutVertically(
            targetOffsetY = { it },
            animationSpec = animationSpec(),
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor),
    ) {
        AnimatedVisibility(
            visible = showSheet,
            enter = enter,
            exit = exit,
        ) {
            Surface(
                color = containerColor,
            ) {
                Column {
                    content()
                }
            }
        }
    }
}

sealed interface ModalSheet {
    data object None : ModalSheet
    data object MenuDrawer : ModalSheet
    data object SpeedDating : ModalSheet
    data object MatcherFilter : ModalSheet
    data class Complement(val imageId: String) : ModalSheet
    data class AboutMe(val aboutMeAttr: AboutMeAttr) : ModalSheet
    data object Location : ModalSheet
    data object SuperSwipe : ModalSheet
    data class HideAndReport(val userId: String) : ModalSheet
    data class RecommendToFriend(val userId: String) : ModalSheet
    data object Settings : ModalSheet
    data object QrCode : ModalSheet
    data object GetPremium : ModalSheet

    data object MyInterests : ModalSheet
    data object OpeningQuestion : ModalSheet
    data object Languages : ModalSheet
    data object Children : ModalSheet
    data object Diet : ModalSheet
    data object Drinking : ModalSheet
    data object Fitness : ModalSheet
    data object Education : ModalSheet
    data object Height : ModalSheet
    data object LoveLanguage : ModalSheet
    data object Personality : ModalSheet
    data object Pets : ModalSheet
    data object Politics : ModalSheet
    data object Religion : ModalSheet
    data object Gender : ModalSheet
    data object Relationship : ModalSheet
    data object Smoking : ModalSheet
    data object Zodiac : ModalSheet
}
