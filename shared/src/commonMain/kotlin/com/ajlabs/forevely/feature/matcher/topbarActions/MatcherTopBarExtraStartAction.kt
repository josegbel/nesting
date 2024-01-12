package com.ajlabs.forevely.feature.matcher.topbarActions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Undo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString

@Composable
internal fun MatcherTopBarExtraStartAction(
    isVisible: Boolean,
    onClick: () -> Unit,
    size: Dp = 32.dp,
    contentDescription: String = getString(Strings.Matcher.UndoSwipe),
) {
    val interactionSource = remember { MutableInteractionSource() }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Icon(
            imageVector = Icons.Outlined.Undo, // TODO This will need custom icon
            contentDescription = contentDescription,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .size(size)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                ),
        )
    }
    if (!isVisible) {
        Box(modifier = Modifier.size(size))
    }
}
