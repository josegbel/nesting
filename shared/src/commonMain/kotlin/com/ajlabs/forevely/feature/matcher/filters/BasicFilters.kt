package com.ajlabs.forevely.feature.matcher.filters

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ajlabs.forevely.components.CheckBoxSectionItem
import com.ajlabs.forevely.components.LabeledSection
import com.ajlabs.forevely.components.RangeSliderSectionItem
import com.ajlabs.forevely.components.ShowMoreSectionItem
import com.ajlabs.forevely.components.SliderSectionItem
import com.ajlabs.forevely.components.SwitchSectionItem
import com.ajlabs.forevely.data.type.Gender
import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.data.type.Relationship
import com.ajlabs.forevely.feature.matcher.cards.MatcherContract
import com.ajlabs.forevely.feature.matcher.cards.MatcherViewModel
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.localization.toName

internal fun LazyListScope.basicFilters(
    vm: MatcherViewModel,
    state: MatcherContract.State,
) {
    item { GendersSection(vm, state) }
    item { RelationshipSection(vm, state) }
    item { AgeSection(vm, state) }
    item { MaxDistanceSection(vm, state) }
    item { LanguagesSection(vm, state) }
}

@Composable
private fun GendersSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
) {
    LabeledSection(
        title = getString(Strings.User.Gender.Question),
    ) {
        Gender.entries
            .filter { it == Gender.MALE || it == Gender.FEMALE || it == Gender.NON_BINARY }
            .forEachIndexed { index, gender ->
                if (index != 0) Divider(Modifier.fillMaxWidth())
                CheckBoxSectionItem(
                    title = getString(gender.toName()),
                    value = gender in state.filters.genders,
                    onValueChange = {
                        vm.trySend(MatcherContract.Inputs.OnGenderClicked(gender))
                    },
                )
            }
    }
}

@Composable
private fun RelationshipSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
) {
    LabeledSection(
        title = getString(Strings.User.Relationship.Question),
    ) {
        Relationship.entries
            .filter { it != Relationship.UNKNOWN__ }
            .forEachIndexed { index, relationshipPreference ->
                if (index != 0) Divider(Modifier.fillMaxWidth())
                CheckBoxSectionItem(
                    title = getString(relationshipPreference.toName()),
                    value = relationshipPreference in state.filters.relationships,
                    onValueChange = {
                        vm.trySend(MatcherContract.Inputs.OnRelationshipClicked(relationshipPreference))
                    },
                )
            }
    }
}

@Composable
private fun AgeSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
) {
    LabeledSection(
        title = getString(Strings.Matcher.AgePrefQuestion),
    ) {
        RangeSliderSectionItem(
            title = getString(
                Strings.Matcher.AgeRange,
                state.filters.ageChosenRange.start.toInt(),
                state.filters.ageChosenRange.endInclusive.toInt()
            ),
            valueRange = state.ageRange,
            value = state.filters.ageChosenRange,
            onValueChange = { vm.trySend(MatcherContract.Inputs.SetAgeChosenRange(it)) },
        )
        SwitchSectionItem(
            title = getString(Strings.Matcher.AgeSafeMargin),
            value = state.filters.hasAgeSafeMargin,
            onValueChange = { vm.trySend(MatcherContract.Inputs.SetHasAgeSafeMargin(it)) },
        )
    }
}

@Composable
private fun MaxDistanceSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
) {
    val textColor by animateColorAsState(
        if (state.filters.hasDistanceLimit) {
            MaterialTheme.colors.onBackground
        } else {
            MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        },
    )
    LabeledSection(
        title = getString(Strings.Matcher.MaxDistancePrefQuestion),
    ) {
        SwitchSectionItem(
            title = getString(Strings.Matcher.DistanceNoLimit),
            value = state.filters.hasDistanceLimit,
            onValueChange = { vm.trySend(MatcherContract.Inputs.SetHasDistanceLimit(it)) },
        )
        SliderSectionItem(
            title = getString(Strings.Matcher.MaxDistancePrefRange, state.filters.maxDistanceAway.toInt()),
            value = state.filters.maxDistanceAway,
            enabled = state.filters.hasDistanceLimit,
            onValueChange = { vm.trySend(MatcherContract.Inputs.SetMaxDistanceAway(it)) },
            textColor = textColor,
        )
        SwitchSectionItem(
            title = getString(Strings.Matcher.MaxDistanceSafeMargin),
            value = state.filters.hasDistanceSafeMargin,
            enabled = state.filters.hasDistanceLimit,
            onValueChange = { vm.trySend(MatcherContract.Inputs.SetHasDistanceSafeMargin(it)) },
            textColor = textColor,
        )
    }
}

@Composable
private fun LanguagesSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
) {
    LabeledSection(
        title = getString(Strings.Matcher.KnownLanguagesPrefQuestion),
    ) {
        ShowMoreSectionItem(
            title = getString(Strings.Matcher.KnownLanguagesSelectLanguages),
        ) {
            Language.entries.forEachIndexed { index, language ->
                if (index != 0) Divider(Modifier.fillMaxWidth())
                CheckBoxSectionItem(
                    title = language.name,
                    value = language in state.filters.languages,
                    onValueChange = {
                        vm.trySend(MatcherContract.Inputs.OnLanguageClicked(language))
                    },
                )
            }
        }
    }
}
