package com.ajlabs.forevely.feature.matcher.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.theme.Colors

@Composable
internal fun RecommendToFriendButton(
    modifier: Modifier = Modifier,
    onCLick: () -> Unit,
    fontSize: TextUnit = 16.sp,
    bgColor: Color = Colors.veryLightGray,
    contentColor: Color = Color.DarkGray,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp,
        color = bgColor,
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onCLick,
            ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Text(
                text = getString(Strings.Matcher.RecommendToFriendButton),
                color = contentColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
