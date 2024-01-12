package com.ajlabs.forevely.feature.matcher.filters

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.feature.matcher.cards.FilterTab
import com.ajlabs.forevely.feature.matcher.cards.MatcherContract
import com.ajlabs.forevely.feature.matcher.cards.MatcherViewModel
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.theme.Dimensions
import com.ajlabs.forevely.theme.safePaddingValues

@Composable
internal fun MatcherFiltersSheet(
    vm: MatcherViewModel,
    state: MatcherContract.State,
    onClose: () -> Unit,
) {
    val actionButtonText = when (state.filterTab) {
        FilterTab.BASIC -> Strings.Apply
        FilterTab.ADVANCED -> Strings.GetPremium
    }

    fun actionButtonOnClick() {
        when (state.filterTab) {
            FilterTab.BASIC -> {
                vm.trySend(MatcherContract.Inputs.ApplyFilters)
                onClose()
            }

            FilterTab.ADVANCED -> {
                if (state.isPremium) {
                    vm.trySend(MatcherContract.Inputs.ApplyFilters)
                    onClose()
                } else {
                    vm.trySend(MatcherContract.Inputs.OnGetPremiumClicked)
                }
            }
        }
    }

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    top = (Dimensions.TOP_BAR_HEIGHT + 56).dp + safePaddingValues.calculateTopPadding(),
                    bottom = (Dimensions.BOTTOM_BAR_HEIGHT + 24).dp + safePaddingValues.calculateBottomPadding(),
                ),
                modifier = Modifier.fillMaxWidth(),
            ) {
                when (state.filterTab) {
                    FilterTab.BASIC -> basicFilters(vm, state)
                    FilterTab.ADVANCED -> advancedFilters(vm, state)
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(
                        top = safePaddingValues.calculateTopPadding(),
                        bottom = safePaddingValues.calculateBottomPadding(),
                    )
            ) {
                Header(
                    onClose = onClose,
                )
                FilterTabs(
                    selectedTab = state.filterTab,
                    onTabSelected = { vm.trySend(MatcherContract.Inputs.SetFilterType(it)) },
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                )
            }
            ActionButton(
                text = getString(actionButtonText),
                onClick = { actionButtonOnClick() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun Header(
    onClose: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .padding(bottom = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = getString(Strings.CloseButton),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(32.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClose,
                ),
        )
        Text(
            text = getString(Strings.Matcher.FiltersLabel),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun FilterTabs(
    selectedTab: FilterTab,
    onTabSelected: (FilterTab) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            FilterTab.entries.forEach { type ->
                val interactionSource = remember { MutableInteractionSource() }
                val filterTabText = getString(
                    when (type) {
                        FilterTab.BASIC -> Strings.Matcher.BasicFilters
                        FilterTab.ADVANCED -> Strings.Matcher.AdvancedFilters
                    },
                )
                val bgColor by animateColorAsState(
                    if (type == selectedTab) MaterialTheme.colors.onBackground else MaterialTheme.colors.background,
                )
                val textColor by animateColorAsState(
                    if (type == selectedTab) MaterialTheme.colors.background else MaterialTheme.colors.onBackground,
                )

                Surface(
                    color = bgColor,
                    shape = RoundedCornerShape(percent = 50),
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onTabSelected(type) },
                        ),
                ) {
                    Text(
                        text = filterTabText,
                        color = textColor,
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = safePaddingValues.calculateBottomPadding() / 2)
        ) {
            Divider(Modifier.fillMaxWidth())
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(percent = 50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
                    .padding(bottom = 16.dp),
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(4.dp),
                )
            }
        }
    }
}
