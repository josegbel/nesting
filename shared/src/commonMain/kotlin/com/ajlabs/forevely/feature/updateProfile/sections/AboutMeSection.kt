package com.ajlabs.forevely.feature.updateProfile.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.feature.updateProfile.ChildrenSubsection
import com.ajlabs.forevely.feature.updateProfile.ContentCard
import com.ajlabs.forevely.feature.updateProfile.DietSubsection
import com.ajlabs.forevely.feature.updateProfile.DrinkingSubsection
import com.ajlabs.forevely.feature.updateProfile.EducationSubsection
import com.ajlabs.forevely.feature.updateProfile.FitnessSubsection
import com.ajlabs.forevely.feature.updateProfile.GenderSubsection
import com.ajlabs.forevely.feature.updateProfile.HeightSubsection
import com.ajlabs.forevely.feature.updateProfile.LoveLanguageSubsection
import com.ajlabs.forevely.feature.updateProfile.PersonalitySubsection
import com.ajlabs.forevely.feature.updateProfile.PetsSubsection
import com.ajlabs.forevely.feature.updateProfile.PoliticsSubsection
import com.ajlabs.forevely.feature.updateProfile.RelationshipSubsection
import com.ajlabs.forevely.feature.updateProfile.ReligionSubsection
import com.ajlabs.forevely.feature.updateProfile.SmokingSubsection
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.State
import com.ajlabs.forevely.feature.updateProfile.ZodiacSubsection
import com.ajlabs.forevely.feature.updateProfile.getFlagEmoji
import com.ajlabs.forevely.feature.updateProfile.getString
import com.ajlabs.forevely.localization.Strings.UpdateProfile.AboutMeSectionSubtitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.AboutMeSectionTitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.LanguagesSectionTitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.NoAboutMe
import com.ajlabs.forevely.localization.Strings.UpdateProfile.NoLanguage
import com.ajlabs.forevely.localization.getString

@Composable
internal fun AboutMeSection(
    state: State,
    onGenderClicked: () -> Unit,
    onChildrenClicked: () -> Unit,
    onDietClicked: () -> Unit,
    onDrinkingClicked: () -> Unit,
    onEducationClicked: () -> Unit,
    onFitnessClicked: () -> Unit,
    onHeightClicked: () -> Unit,
    onLoveLanguageClicked: () -> Unit,
    onPersonalityClicked: () -> Unit,
    onPetsClicked: () -> Unit,
    onPoliticsClicked: () -> Unit,
    onRelationshipClicked: () -> Unit,
    onReligionClicked: () -> Unit,
    onSmokingClicked: () -> Unit,
    onZodiacClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(getString(AboutMeSectionTitle), style = MaterialTheme.typography.h6)
        Spacer(Modifier.padding(4.dp))
        Text(getString(AboutMeSectionSubtitle))
        Spacer(Modifier.padding(8.dp))
        Box(contentAlignment = Alignment.Center) {
            if (state.cachedUser?.profile?.aboutMe == null) {
                Text(getString(NoAboutMe))
                Spacer(Modifier.padding(8.dp))
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    GenderSubsection(value = state.cachedUser?.details?.gender, onClick = { onGenderClicked() })
                    ChildrenSubsection(
                        value = state.cachedUser?.profile?.aboutMe?.children,
                        onClick = { onChildrenClicked() })
                    DietSubsection(value = state.cachedUser?.profile?.aboutMe?.diet, onClick = { onDietClicked() })
                    DrinkingSubsection(
                        value = state.cachedUser?.profile?.aboutMe?.drinking,
                        onClick = { onDrinkingClicked() })
                    EducationSubsection(
                        value = state.cachedUser?.profile?.aboutMe?.education,
                        onClick = { onEducationClicked() })
                    FitnessSubsection(value = state.cachedUser?.profile?.aboutMe?.fitness, onClick = { onFitnessClicked() })
                    HeightSubsection(value = state.cachedUser?.profile?.aboutMe?.height, onClick = { onHeightClicked() })
                    LoveLanguageSubsection(
                        value = state.cachedUser?.profile?.aboutMe?.loveLanguage,
                        onClick = { onLoveLanguageClicked() })
                    PersonalitySubsection(
                        value = state.cachedUser?.profile?.aboutMe?.personality,
                        onClick = { onPersonalityClicked() })
                    PetsSubsection(value = state.cachedUser?.profile?.aboutMe?.pets, onClick = { onPetsClicked() })
                    PoliticsSubsection(
                        value = state.cachedUser?.profile?.aboutMe?.politics,
                        onClick = { onPoliticsClicked() })
                    RelationshipSubsection(
                        value = state.cachedUser?.profile?.aboutMe?.relationship,
                        onClick = { onRelationshipClicked() })
                    ReligionSubsection(
                        value = state.cachedUser?.profile?.aboutMe?.religion,
                        onClick = { onReligionClicked() })
                    SmokingSubsection(value = state.cachedUser?.profile?.aboutMe?.smoking, onClick = { onSmokingClicked() })
                    ZodiacSubsection(value = state.cachedUser?.profile?.aboutMe?.zodiac, onClick = { onZodiacClicked() })
                }
            }
        }
    }
}

@Composable
internal fun LanguagesSection(languages: List<Language>?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(getString(LanguagesSectionTitle), style = MaterialTheme.typography.h6)
        Spacer(Modifier.padding(8.dp))
        ContentCard(
            modifier = Modifier.fillMaxWidth()
                .clickable { onClick() }
        ) {
            if (languages.isNullOrEmpty()) {
                Box(contentAlignment = Alignment.Center) {
                    Text(getString(NoLanguage))
                    Spacer(Modifier.padding(8.dp))
                }
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    languages.forEach { language ->
                        Surface(shape = RoundedCornerShape(16.dp)) {
                            Text(
                                text = "${getFlagEmoji(language)} ${language.getString()}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
