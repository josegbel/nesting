package com.ajlabs.forevely.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.theme.Colors
import kotlinx.coroutines.delay

@Composable
internal fun NameAgeVerifiedRow(
    modifier: Modifier = Modifier,
    userName: String?,
    age: Int?,
    isVerified: Boolean,
    textColor: Color = MaterialTheme.colors.onBackground,
    fontSize: TextUnit = 32.sp,
    verifiedIconSize: Dp = 24.dp,
    verifiedColor: Color = Colors.blue,
    notVerifiedColor: Color = Color.Gray,
) {
    var isShowing by remember { mutableStateOf(false) }
    val verifiedColorState by animateColorAsState(
        targetValue = if (isShowing) verifiedColor else notVerifiedColor,
        animationSpec = tween(500),
    )
    val rotation by animateFloatAsState(
        targetValue = if (isShowing) 0f else 360f,
        animationSpec = tween(1000),
    )

    LaunchedEffect(Unit) {
        delay(500)
        isShowing = isVerified
    }

    if (userName != null) {
        val separator = age?.let { "," } ?: ""

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier,
        ) {
            Text(
                text = "$userName${separator}",
                fontSize = fontSize,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
            )
            age?.let {
                Text(
                    text = it.toString(),
                    fontSize = fontSize,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            VerifiedIcon(
                color = verifiedColorState,
                size = verifiedIconSize,
                iconRotation = rotation,
            )
        }
    }
}

@Composable
internal fun VerifiedIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color,
    iconRotation: Float = 0f,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Shield,
            tint = color,
            contentDescription = getString(Strings.User.Verified),
            modifier = modifier.size(size),
        )
        Icon(
            imageVector = Icons.Rounded.Star,
            tint = MaterialTheme.colors.background,
            contentDescription = null,
            modifier = modifier
                .size(size / 2)
                .rotate(iconRotation)
                .padding(bottom = (0.5).dp),
        )
    }
}
