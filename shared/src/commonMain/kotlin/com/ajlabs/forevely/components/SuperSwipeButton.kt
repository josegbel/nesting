package com.ajlabs.forevely.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.theme.HexagonShape
import com.ajlabs.forevely.theme.drawCustomHexagonPath

@Composable
internal fun SuperSwipeButton(
    modifier: Modifier = Modifier,
    bgColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    contentDescription: String = "Super Swipe",
    onClick: () -> Unit,
) {
    val mutableInteractionSource = MutableInteractionSource()

    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(64.dp)
            .drawWithContent {
                drawContent()
                drawPath(
                    path = drawCustomHexagonPath(size),
                    color = bgColor,
                    style = Stroke(
                        width = 10.dp.toPx(),
                        pathEffect = PathEffect.cornerPathEffect(10f),
                    ),
                )
            }
            .wrapContentSize(),
    ) {
        Surface(
            color = bgColor,
            modifier = modifier
                .clickable(
                    interactionSource = mutableInteractionSource,
                    indication = null,
                    onClick = onClick,
                )
                .graphicsLayer {
                    shadowElevation = 0.dp.toPx()
                    shape = HexagonShape
                    clip = true
                },
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = contentDescription,
                    tint = contentColor,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(44.dp),
                )
            }
        }
    }
}
