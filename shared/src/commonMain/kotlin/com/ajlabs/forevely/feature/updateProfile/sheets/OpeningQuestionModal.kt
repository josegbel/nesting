package com.ajlabs.forevely.feature.updateProfile.sheets

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.SimpleTopBar
import com.ajlabs.forevely.data.type.OpenQuestion
import com.ajlabs.forevely.data.type.OpenQuestion.ACHIEVEMENT
import com.ajlabs.forevely.data.type.OpenQuestion.BIGGEST_FEAR
import com.ajlabs.forevely.data.type.OpenQuestion.CHANGE_WORLD
import com.ajlabs.forevely.data.type.OpenQuestion.CHILDHOOD_MEMORY
import com.ajlabs.forevely.data.type.OpenQuestion.DINNER_GUEST
import com.ajlabs.forevely.data.type.OpenQuestion.FAVORITE_QUOTE
import com.ajlabs.forevely.data.type.OpenQuestion.HISTORICAL_ERA
import com.ajlabs.forevely.data.type.OpenQuestion.IMPACTFUL_BOOK
import com.ajlabs.forevely.data.type.OpenQuestion.LAUGHTER_MOMENT
import com.ajlabs.forevely.data.type.OpenQuestion.LEARN_SKILL
import com.ajlabs.forevely.data.type.OpenQuestion.PASSION
import com.ajlabs.forevely.data.type.OpenQuestion.PEACE_SOURCE
import com.ajlabs.forevely.data.type.OpenQuestion.PERFECT_DAY
import com.ajlabs.forevely.data.type.OpenQuestion.SPONTANEOUS_ACT
import com.ajlabs.forevely.data.type.OpenQuestion.UNKNOWN__
import com.ajlabs.forevely.data.type.OpenQuestion.UNWIND_METHOD
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.State
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import kotlinx.coroutines.launch

@Composable
internal fun OpeningQuestionModal(
    onCloseClicked: suspend () -> Unit,
    state: State,
    onSaveClicked: (OpenQuestion) -> Unit,
    onItemSelected: (OpenQuestion) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Surface(color = MaterialTheme.colors.background) {
        Column {
            SimpleTopBar(
                headerText = getString(Strings.OpenQuestion.ModalTitle),
                onBackClicked = { scope.launch { onCloseClicked() } }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = getString(Strings.OpenQuestion.ModalSubtitle),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 60.dp)
                    ) {
                        OpenQuestion.values()
                            .filter { it != UNKNOWN__ }
                            .forEach { question ->
                                item {
                                    OpenQuestionCard(
                                        openQuestion = question,
                                        isSelected = state.uiUser?.profile?.openQuestion == question,
                                        onSelect = { onItemSelected(it) }
                                    )
                                }
                            }
                    }
                    Button(
                        onClick = {
                            state.uiUser?.profile?.openQuestion?.let { onSaveClicked(it) }
                            scope.launch { onCloseClicked() }
                        },
                        enabled = state.uiUser?.profile?.openQuestion != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                    ) {
                        Text(
                            text = getString(Strings.Save),
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OpenQuestionCard(
    openQuestion: OpenQuestion,
    isSelected: Boolean,
    onSelect: (OpenQuestion) -> Unit,
) {
    val borderColor by animateColorAsState(
        if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray,
    )

    Surface(
        color = if (isSelected) MaterialTheme.colors.surface else MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.medium,
        border = if (isSelected) null else BorderStroke(1.dp, color = borderColor),
        modifier = Modifier.clickable { onSelect(OpenQuestion.values().first { it == openQuestion }) },
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = getString(openQuestion.stringKey()),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.body1,
            )
            RadioButton(
                selected = isSelected,
                onClick = { onSelect(OpenQuestion.values().first { it == openQuestion }) },
                interactionSource = remember { MutableInteractionSource() },
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

fun OpenQuestion.stringKey(): Strings {
    return when (this) {
        ACHIEVEMENT -> Strings.OpenQuestion.Achievement
        HISTORICAL_ERA -> Strings.OpenQuestion.HistoricalEra
        IMPACTFUL_BOOK -> Strings.OpenQuestion.ImpactfulBook
        UNWIND_METHOD -> Strings.OpenQuestion.UnwindMethod
        DINNER_GUEST -> Strings.OpenQuestion.DinnerGuest
        LEARN_SKILL -> Strings.OpenQuestion.LearnSkill
        CHILDHOOD_MEMORY -> Strings.OpenQuestion.ChildhoodMemory
        SPONTANEOUS_ACT -> Strings.OpenQuestion.SpontaneousAct
        BIGGEST_FEAR -> Strings.OpenQuestion.BiggestFear
        PASSION -> Strings.OpenQuestion.Passion
        PERFECT_DAY -> Strings.OpenQuestion.PerfectDay
        LAUGHTER_MOMENT -> Strings.OpenQuestion.LaughterMoment
        PEACE_SOURCE -> Strings.OpenQuestion.PeaceSource
        CHANGE_WORLD -> Strings.OpenQuestion.ChangeWorld
        FAVORITE_QUOTE -> Strings.OpenQuestion.FavoriteQuote
        UNKNOWN__ -> Strings.Unknown
    }
}
