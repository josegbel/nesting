package com.ajlabs.forevely.feature.matcher.sections

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.components.ComplementButton
import com.ajlabs.forevely.components.NameAgeVerifiedRow
import com.ajlabs.forevely.theme.Colors
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
internal fun MainPictureSection(
    modifier: Modifier = Modifier,
    name: String,
    age: Int,
    imageUrl: String,
    verified: Boolean,
    onComplementCLicked: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        KamelImage(
            resource = asyncPainterResource(imageUrl),
            contentDescription = "Thumbnail",
            contentScale = ContentScale.Crop,
            onLoading = { progress -> CircularProgressIndicator(progress) },
            animationSpec = tween(),
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Colors.darkGreyTransparent.copy(alpha = 0.5f),
                            Colors.darkGreyTransparent,
                            Colors.darkGreyTransparent,
                        ),
                    ),
                ),
        ) {
            BottomLeftSection(
                modifier = Modifier
                    .padding(16.dp),
                name = name,
                age = age,
                isVerified = verified,
                onComplementCLicked = onComplementCLicked,
            )
        }
    }
}

@Composable
private fun BottomLeftSection(
    modifier: Modifier = Modifier,
    name: String,
    age: Int,
    isVerified: Boolean,
    onComplementCLicked: () -> Unit,
    textColor: Color = Color.White,
    fontSize: TextUnit = 32.sp,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        NameAgeVerifiedRow(
            userName = name,
            age = age,
            isVerified = isVerified,
            textColor = textColor,
            fontSize = fontSize,
        )
        ComplementButton(
            onClick = onComplementCLicked,
        )
    }
}
