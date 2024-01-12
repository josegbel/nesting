package com.ajlabs.forevely.feature.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.components.Thumbnail

@Composable
internal fun ChatTopBar(
    modifier: Modifier,
    title: String?,
    thumb: String?,
    onMenuClick: () -> Unit,
    onGoBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(16.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos,
                contentDescription = "Go back",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = onGoBackClick,
                    ),
            )
            thumb?.let {
                Spacer(modifier = Modifier.size(4.dp))
                Thumbnail(
                    url = it,
                    modifier = Modifier.size(48.dp),
                )
            }
            title?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
            }
            ThreeDotMenu(onClick = onMenuClick)
        }
    }
}

@Composable
private fun ThreeDotMenu(
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .size(24.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick,
            ),
    ) {
        repeat(3) {
            Surface(
                color = MaterialTheme.colors.onBackground,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(4.dp)
            ) {}
        }
    }
}
