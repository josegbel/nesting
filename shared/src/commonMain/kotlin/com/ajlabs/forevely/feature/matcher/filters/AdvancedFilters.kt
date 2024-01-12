package com.ajlabs.forevely.feature.matcher.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.components.AddFilterSectionItem
import com.ajlabs.forevely.components.CheckBoxSectionItem
import com.ajlabs.forevely.components.LabeledSection
import com.ajlabs.forevely.components.RangeSliderSectionItem
import com.ajlabs.forevely.components.SwitchSectionItem
import com.ajlabs.forevely.data.type.Children
import com.ajlabs.forevely.data.type.Diet
import com.ajlabs.forevely.data.type.Drinking
import com.ajlabs.forevely.data.type.Education
import com.ajlabs.forevely.data.type.Fitness
import com.ajlabs.forevely.data.type.LoveLanguage
import com.ajlabs.forevely.data.type.Personality
import com.ajlabs.forevely.data.type.Pet
import com.ajlabs.forevely.data.type.Politics
import com.ajlabs.forevely.data.type.Religion
import com.ajlabs.forevely.data.type.Smoking
import com.ajlabs.forevely.data.type.Zodiac
import com.ajlabs.forevely.feature.matcher.cards.AdvancedFilter
import com.ajlabs.forevely.feature.matcher.cards.MatcherContract
import com.ajlabs.forevely.feature.matcher.cards.MatcherViewModel
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.localization.toName

internal fun LazyListScope.advancedFilters(
    vm: MatcherViewModel,
    state: MatcherContract.State,
) {
    item {
        VerifiedSection(
            vm = vm,
            state = state,
            onWhatsThisClick = {
                // TODO: Open whats this popup
            }
        )
    }
    item { HeightSection(vm, state) }
    item { FitnessSection(vm, state) }
    item { EducationSection(vm, state) }
    item { DrinkingSection(vm, state) }
    item { SmokingSection(vm, state) }
    item { ChildrenSection(vm, state) }
    item { ZodiacSection(vm, state) }
    item { PoliticsSection(vm, state) }
    item { ReligionSection(vm, state) }
    item { DietarySection(vm, state) }
    item { LoveLanguageSection(vm, state) }
    item { PersonalitySection(vm, state) }
    item { PetsSection(vm, state) }
}

@Composable
private fun VerifiedSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    onWhatsThisClick: () -> Unit,
) {
    LabeledSection(
        title = getString(Strings.Matcher.VerifiedPrefQuestion),
        verticalPadding = 4.dp,
        extras = {
            Text(
                text = getString(Strings.Matcher.WhatsThisText),
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 16.dp,
                    )
                    .clickable { onWhatsThisClick() }
            )
        }
    ) {
        SwitchSectionItem(
            title = getString(Strings.Matcher.VerifiedProfilesOnly),
            value = state.filters.verifiedOnly,
            onValueChange = { vm.trySend(MatcherContract.Inputs.OnVerifiedOnlyClicked) },
        )
    }
}

@Composable
private fun HeightSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.HEIGHT,
) {
    val range = state.filters.heightChosenRange
    val chosenRange = state.filters.heightChosenRange
    val minChosen = chosenRange.start.toInt()
    val maxChosen = chosenRange.endInclusive.toInt()
    val heightPrefTitle = when {
        minChosen == range.start.toInt() && maxChosen == range.endInclusive.toInt() ->
            getString(Strings.Matcher.HeightPref.AnyHeightIsFine)

        minChosen != range.start.toInt() && maxChosen == range.endInclusive.toInt() ->
            getString(Strings.Matcher.HeightPref.TallerThan, minChosen)

        minChosen == range.start.toInt() && maxChosen != range.endInclusive.toInt() ->
            getString(Strings.Matcher.HeightPref.ShorterThan, minChosen)

        else -> getString(Strings.Matcher.HeightPref.ValueText, minChosen, maxChosen)
    }
    LabeledSection(
        title = getString(Strings.Matcher.HeightPref.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.FitnessCenter,
        ) {
            RangeSliderSectionItem(
                title = heightPrefTitle,
                valueRange = 100f..200f,
                value = state.filters.heightChosenRange,
                onValueChange = { vm.trySend(MatcherContract.Inputs.SetHeightChosenRange(it)) },
            )
            SwitchSectionItem(
                title = getString(Strings.ShowOtherPeopleIfIRunOut),
                value = state.filters.heightSafeMargin,
                onValueChange = { vm.trySend(MatcherContract.Inputs.SetHeightSafeRange(it)) },
            )
        }
    }
}

@Composable
private fun FitnessSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.FITNESS,
) {
    LabeledSection(
        title = getString(Strings.User.Fitness.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.FitnessCenter,
        ) {
            Fitness.entries
                .filter { it != Fitness.UNKNOWN__ }
                .forEach { fitnessStatus ->
                    CheckBoxSectionItem(
                        title = getString(fitnessStatus.toName()),
                        value = fitnessStatus in state.filters.fitnesses,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnFitnessClicked(fitnessStatus))
                        },
                    )
                }
        }
    }
}

@Composable
private fun EducationSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.EDUCATION,
) {
    LabeledSection(
        title = getString(Strings.User.Education.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.School,
        ) {
            Education.entries
                .filter { it != Education.UNKNOWN__ }
                .forEach { educationLevel ->
                    CheckBoxSectionItem(
                        title = getString(educationLevel.toName()),
                        value = educationLevel in state.filters.educations,
                        onValueChange = {
                            vm.trySend(
                                MatcherContract.Inputs.OnEducationClicked(educationLevel)
                            )
                        },
                    )
                }
        }
    }
}

@Composable
private fun DrinkingSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.DRINKING,
) {
    LabeledSection(
        title = getString(Strings.User.Drink.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.LocalBar,
        ) {
            Drinking.entries
                .filter { it != Drinking.UNKNOWN__ }
                .forEach { drinkingStatus ->
                    CheckBoxSectionItem(
                        title = getString(drinkingStatus.toName()),
                        value = drinkingStatus in state.filters.drinkings,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnDrinkingClicked(drinkingStatus))
                        },
                    )
                }
        }
    }
}

@Composable
private fun SmokingSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.SMOKING,
) {
    LabeledSection(
        title = getString(Strings.User.Smoking.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.SmokingRooms,
        ) {
            Smoking.entries
                .filter { it != Smoking.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.smokings,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnSmokingClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun ChildrenSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.CHILDREN,
) {
    LabeledSection(
        title = getString(Strings.User.Children.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.SmokingRooms,
        ) {
            Children.entries
                .filter { it != Children.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.children,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnChildClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun ZodiacSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.ZODIAC,
) {
    LabeledSection(
        title = getString(Strings.User.Zodiac.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.Workspaces,
        ) {
            Zodiac.entries
                .filter { it != Zodiac.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.zodiacs,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnZodiacClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun PoliticsSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.POLITICS,
) {
    LabeledSection(
        title = getString(Strings.User.Politics.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.Workspaces,
        ) {
            Politics.entries
                .filter { it != Politics.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.politics,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnPoliticClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun ReligionSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.RELIGION,
) {
    LabeledSection(
        title = getString(Strings.User.Religion.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.Church,
        ) {
            Religion.entries
                .filter { it != Religion.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.religions,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnReligionClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun DietarySection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.DIET,
) {
    LabeledSection(
        title = getString(Strings.User.Diet.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.Church,
        ) {
            Diet.entries
                .filter { it != Diet.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.diets,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnDietClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun LoveLanguageSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.LOVE_LANGUAGE,
) {
    LabeledSection(
        title = getString(Strings.User.LoveLanguage.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.Favorite,
        ) {
            LoveLanguage.entries
                .filter { it != LoveLanguage.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.loveLanguages,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnLoveLanguageClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun PersonalitySection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.PERSONALITY,
) {
    LabeledSection(
        title = getString(Strings.User.Personality.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.Diversity3,
        ) {
            Personality.entries
                .filter { it != Personality.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.personalities,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnPersonalityClicked(status))
                        },
                    )
                }
        }
    }
}

@Composable
private fun PetsSection(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    advancedFilter: AdvancedFilter = AdvancedFilter.PETS,
) {
    LabeledSection(
        title = getString(Strings.User.Pets.Question),
    ) {
        AddFilterSectionItem(
            isAdded = advancedFilter in state.advancedFilters,
            onClick = { vm.trySend(MatcherContract.Inputs.OnAdvancedFilterClicked(advancedFilter)) },
            icon = Icons.Default.Pets,
        ) {
            Pet.entries
                .filter { it != Pet.UNKNOWN__ }
                .forEach { status ->
                    CheckBoxSectionItem(
                        title = getString(status.toName()),
                        value = status in state.filters.pets,
                        onValueChange = {
                            vm.trySend(MatcherContract.Inputs.OnPetClicked(status))
                        },
                    )
                }
        }
    }
}
