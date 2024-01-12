package com.ajlabs.forevely.feature.updateProfile.sheets

import com.ajlabs.forevely.feature.updateProfile.InterestCategory as Category
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.components.LabeledSection
import com.ajlabs.forevely.components.SimpleTopBar
import com.ajlabs.forevely.data.GetUserQuery.Interests
import com.ajlabs.forevely.data.type.Creative
import com.ajlabs.forevely.data.type.Culinary
import com.ajlabs.forevely.data.type.Leisure
import com.ajlabs.forevely.data.type.Mind
import com.ajlabs.forevely.data.type.Nature
import com.ajlabs.forevely.data.type.Social
import com.ajlabs.forevely.data.type.Sports
import com.ajlabs.forevely.data.type.Technology
import com.ajlabs.forevely.feature.matcher.sections.Chip
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.State
import com.ajlabs.forevely.feature.updateProfile.sections.icon
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.Strings.InterestCategory
import com.ajlabs.forevely.localization.Strings.Save
import com.ajlabs.forevely.localization.getString
import kotlinx.coroutines.launch

// FIXME: For now using LocationModal and MyBasicsModal which are the same. Remove location when get there
@Composable
internal fun MyInterestsModal(
    onCloseClicked: suspend () -> Unit,
    onSaveClicked: suspend (Interests?) -> Unit,
    state: State,
    onChipClicked: (interestCategory: Category, name: String, toggleSelection: Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Surface(color = MaterialTheme.colors.background) {
        Column {
            SimpleTopBar(
                headerText = getString(Strings.Interest.ModalTitle),
                onBackClicked = { scope.launch { onCloseClicked() } }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                LabeledSection(
                    title = getString(Strings.Interest.ModalSubtitle),
                    verticalPadding = 0.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp)
                        .animateContentSize(),
                ) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                        item {
                            Text(
                                text = getString(InterestCategory.Sports),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Sports.entries
                                    .filter { it != Sports.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.sports?.contains(it) ?: false
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Sports, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Divider() }
                        item {
                            Text(
                                text = getString(InterestCategory.Culinary),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Culinary.entries
                                    .filter { it != Culinary.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.culinaries?.contains(it) ?: false
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Culinaries, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Divider() }
                        item {
                            Text(
                                text = getString(InterestCategory.Creative),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Creative.entries
                                    .filter { it != Creative.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.creativities?.contains(it) ?: true
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Creativities, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Divider() }
                        item {
                            Text(
                                text = getString(InterestCategory.Leisure),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Leisure.entries
                                    .filter { it != Leisure.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.leisures?.contains(it) ?: false
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Leisures, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Divider() }
                        item {
                            Text(
                                text = getString(InterestCategory.Social),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Social.entries
                                    .filter { it != Social.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.socials?.contains(it) ?: false
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Socials, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Divider() }
                        item {
                            Text(
                                text = getString(InterestCategory.Technology),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Technology.entries
                                    .filter { it != Technology.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.technologies?.contains(it) ?: false
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Technologies, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Divider() }
                        item {
                            Text(
                                text = getString(InterestCategory.Nature),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Nature.entries
                                    .filter { it != Nature.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.natures?.contains(it) ?: false
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Natures, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Divider() }
                        item {
                            Text(
                                text = getString(InterestCategory.Mind),
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                        item {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Mind.entries
                                    .filter { it != Mind.UNKNOWN__ }
                                    .forEach {
                                        val isSelected = state.uiUser?.profile?.interests?.minds?.contains(it) ?: false
                                        Chip(
                                            text = it.name,
                                            icon = it.icon(),
                                            isSelected = isSelected,
                                            onClick = { onChipClicked(Category.Minds, it.name, !isSelected) },
                                        )
                                    }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }
                }
                Button(
                    onClick = {
                        scope.launch {
                            onSaveClicked(state.uiUser?.profile?.interests)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(BottomCenter),
                ) {
                    Text(
                        text = getString(Save),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
            }
        }
    }
}
