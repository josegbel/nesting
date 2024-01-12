package com.ajlabs.forevely.feature.matcher.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.SuperSwipeButton
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString

@Composable
internal fun FinalMatchSection(
    modifier: Modifier = Modifier,
    onSuperSwipeCLick: () -> Unit,
    onSwipeRightClick: () -> Unit,
    onSwipeLeftClick: () -> Unit,
    onHideAndReportClick: () -> Unit,
    bgColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onSurface,
) {
    val hideAndReportInteractionSource = remember { MutableInteractionSource() }

    Surface(
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 24.dp,
            bottomStart = 24.dp,
        ),
        color = bgColor,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            SwipeIcon(
                imageVector = Icons.Default.Close,
                onClick = onSwipeLeftClick,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(16.dp),
            ) {
                SuperSwipeButton(
                    onClick = onSuperSwipeCLick,
                )
                Text(
                    text = getString(Strings.Matcher.HideAndReport),
                    color = contentColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable(
                            interactionSource = hideAndReportInteractionSource,
                            indication = null,
                            onClick = onHideAndReportClick,
                        ),
                )
            }
            SwipeIcon(
                imageVector = Icons.Default.Check,
                onClick = onSwipeRightClick,
            )
        }
    }
}

@Composable
internal fun SwipeIcon(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.primary,
        shape = CircleShape,
        modifier = Modifier
            .size(64.dp)
            .clickable(
                onClick = onClick,
            ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(32.dp),
            )
        }
    }
}
