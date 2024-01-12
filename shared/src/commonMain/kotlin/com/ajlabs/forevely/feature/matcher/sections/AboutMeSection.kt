package com.ajlabs.forevely.feature.matcher.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.ControlPoint
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.feature.matcher.cards.AboutMeAttr
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString

@Composable
internal fun AboutMeSection(
    modifier: Modifier = Modifier,
    title: String = getString(Strings.Matcher.MyBasics),
    items: Map<AboutMeAttr, String>,
    onCLick: (basicInfo: AboutMeAttr) -> Unit,
    titleFontSize: TextUnit = 18.sp,
) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(16.dp),
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.onSurface,
                fontSize = titleFontSize,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items.forEach { item ->
                    Chip(
                        modifier = modifier,
                        onClick = { onCLick(item.key) },
                        text = getString(item.key.text()),
                        icon = item.key.icon(),
                    )
                }
            }
        }
    }
}

@Composable
internal fun Chip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    isSelected: Boolean = false,
    fontSize: TextUnit = 14.sp,
    icon: ImageVector,
    contentDescription: String? = null,
    chipColor: Color = MaterialTheme.colors.primary,
) {
    val mutableInteractionSource = remember { MutableInteractionSource() }

    val unselectedColor = MaterialTheme.colors.background
    val strokeColor = MaterialTheme.colors.primaryVariant

    Surface(
        shape = RoundedCornerShape(20.dp),
        border = if (isSelected) null else BorderStroke(1.dp, strokeColor),
        elevation = 0.dp,
        color = if(isSelected) chipColor else unselectedColor,
        modifier = modifier
            .clickable(
                interactionSource = mutableInteractionSource,
                indication = null,
                onClick = onClick,
            ),
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                vertical = 4.dp,
                horizontal = 8.dp,
            ),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(fontSize.value.dp),
            )
            Text(
                text = text,
                fontSize = fontSize,
            )
        }
    }
}

internal fun AboutMeAttr.text() = when (this) {
    AboutMeAttr.HEIGHT -> Strings.User.Height.Name
    AboutMeAttr.DIET -> Strings.User.Diet.Name
    AboutMeAttr.DRINKING -> Strings.User.Drink.Name
    AboutMeAttr.EDUCATION -> Strings.User.Education.Name
    AboutMeAttr.FITNESS -> Strings.User.Fitness.Name
    AboutMeAttr.LOVE_LANGUAGE -> Strings.User.LoveLanguage.Name
    AboutMeAttr.PERSONALITY -> Strings.User.Personality.Name
    AboutMeAttr.PETS -> Strings.User.Pets.Name
    AboutMeAttr.POLITICS -> Strings.User.Politics.Name
    AboutMeAttr.RELATIONSHIP -> Strings.User.Relationship.Name
    AboutMeAttr.SMOKING -> Strings.User.Smoking.Name
    AboutMeAttr.CHILDREN -> Strings.User.Children.Name
    AboutMeAttr.ZODIAC -> Strings.User.Zodiac.Name
    AboutMeAttr.RELIGION -> Strings.User.Religion.Name
}

internal fun AboutMeAttr.icon(): ImageVector = when (this) {
    AboutMeAttr.HEIGHT -> Icons.Default.Straighten
    AboutMeAttr.FITNESS -> Icons.Default.FitnessCenter
    AboutMeAttr.EDUCATION -> Icons.Default.School
    AboutMeAttr.DRINKING -> Icons.Default.LocalBar
    AboutMeAttr.SMOKING -> Icons.Default.SmokingRooms
    AboutMeAttr.RELATIONSHIP -> Icons.Default.Search
    AboutMeAttr.CHILDREN -> Icons.Default.ChildFriendly
    AboutMeAttr.ZODIAC -> Icons.Default.Stars
    AboutMeAttr.RELIGION -> Icons.Default.Church
    AboutMeAttr.DIET -> Icons.Default.BreakfastDining
    AboutMeAttr.LOVE_LANGUAGE -> Icons.Default.Favorite
    AboutMeAttr.PERSONALITY -> Icons.Default.Person
    AboutMeAttr.PETS -> Icons.Default.Pets
    AboutMeAttr.POLITICS -> Icons.Default.ControlPoint
}
