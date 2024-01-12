package com.ajlabs.forevely.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun ComplementButton(
    modifier: Modifier = Modifier,
    bgColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    contentDescription: String = "Complement",
    onClick: () -> Unit,
) {
    val mutableInteractionSource = MutableInteractionSource()

    Surface(
        shape = CircleShape,
        color = bgColor,
        modifier = modifier
            .clickable(
                interactionSource = mutableInteractionSource,
                indication = null,
                onClick = onClick,
            ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.ChatBubble,
                contentDescription = contentDescription,
                tint = contentColor,
                modifier = Modifier
                    .offset(x = 0.dp, y = (1.5).dp)
                    .padding(8.dp),
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = contentDescription,
                tint = bgColor,
                modifier = Modifier.size(8.dp),
            )
        }
    }
}
