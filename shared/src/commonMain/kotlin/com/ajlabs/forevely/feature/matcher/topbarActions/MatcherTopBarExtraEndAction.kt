package com.ajlabs.forevely.feature.matcher.topbarActions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString

@Composable
internal fun MatcherTopBarExtraEndAction(
    onClick: () -> Unit,
    contentDescription: String = getString(Strings.Matcher.SpeedDatingIcon),
) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        imageVector = Icons.Default.AlarmOn,
        contentDescription = contentDescription,
        tint = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .size(32.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    )
}
