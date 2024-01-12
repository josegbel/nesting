package com.ajlabs.forevely.feature.matcher.sections

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.ComplementButton
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PicturesSection(
    modifier: Modifier = Modifier,
    pictures: List<String>,
    onComplementCLicked: (imageUrl: String) -> Unit,
) {
    pictures.forEach {
        Box(
            modifier = modifier,
        ) {
            KamelImage(
                resource = asyncPainterResource(it),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { progress -> CircularProgressIndicator(progress) },
                animationSpec = tween(),
                modifier = Modifier.fillMaxSize(),
            )
            ComplementButton(
                onClick = { onComplementCLicked(it) },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
            )
        }
    }
}
