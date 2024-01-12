package com.ajlabs.forevely.feature.updateProfile.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.VerifiedIcon
import com.ajlabs.forevely.feature.updateProfile.model.ProfilePhoto
import com.ajlabs.forevely.localization.Strings.UpdateProfile.FifthPictureContentDescription
import com.ajlabs.forevely.localization.Strings.UpdateProfile.FirstPictureContentDescription
import com.ajlabs.forevely.localization.Strings.UpdateProfile.ForthPictureContentDescription
import com.ajlabs.forevely.localization.Strings.UpdateProfile.MediaSectionSubtitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.MediaSectionTitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.NotVerified
import com.ajlabs.forevely.localization.Strings.UpdateProfile.SecondPictureContentDescription
import com.ajlabs.forevely.localization.Strings.UpdateProfile.SixthPictureContentDescription
import com.ajlabs.forevely.localization.Strings.UpdateProfile.ThirdPictureContentDescription
import com.ajlabs.forevely.localization.Strings.UpdateProfile.Verified
import com.ajlabs.forevely.localization.Strings.UpdateProfile.VerifyMyProfile
import com.ajlabs.forevely.localization.getString
import com.ajlabs.forevely.theme.Colors
import com.ajlabs.forevely.util.rememberBitmapFromBytes
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay

@Composable
fun MediaSection(
    userName: String,
    isVerified: Boolean = false,
    verifiedIconSize: Dp = 24.dp,
    verifiedColor: Color = Colors.blue,
    notVerifiedColor: Color = Color.Gray,
    profilePhotos: List<ProfilePhoto>,
    onImageClicked: (Int) -> Unit,
) {
    val verifiedColorState by animateColorAsState(
        targetValue = if (isVerified) verifiedColor else notVerifiedColor,
        animationSpec = tween(500),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(getString(MediaSectionTitle), style = MaterialTheme.typography.h6)
        Spacer(Modifier.padding(4.dp))
        Text(getString(MediaSectionSubtitle))
        Spacer(Modifier.padding(12.dp))
        PhotoGrid(userName, profilePhotos, onImageClicked)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 24.dp)
        ) {
            VerifiedIcon(
                color = verifiedColorState,
                size = verifiedIconSize,
            )
            Text(
                text = getString(VerifyMyProfile),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (isVerified) getString(Verified) else getString(NotVerified),
                color = verifiedColorState,
            )
            if (!isVerified) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = getString(VerifyMyProfile),
                    tint = verifiedColorState,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
internal fun NotFoundImage() {
    val url = "https://webcolours.ca/wp-content/uploads/2020/10/webcolours-unknown.png"
    KamelImage(
        resource = asyncPainterResource(url),
        contentDescription = "Thumbnail",
        contentScale = ContentScale.Crop,
        onLoading = { progress -> CircularProgressIndicator(progress) },
        animationSpec = tween(),
    )
}

@Composable
internal fun PhotoGrid(userName: String, photos: List<ProfilePhoto>, onImageClicked: (Int) -> Unit) {

    BoxWithConstraints {

        val height = this.maxWidth
        var gridItemSpacing by remember { mutableStateOf(4.dp) }
        val spacingAnim by animateDpAsState(
            targetValue = gridItemSpacing,
            animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
        )

        LaunchedEffect(true) {
            delay(100L)
            gridItemSpacing = 8.dp
        }

        Column(
            verticalArrangement = Arrangement.run { spacedBy(spacingAnim) },
            modifier = Modifier.height(height)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacingAnim),
                modifier = Modifier.weight(2f)
            ) {
                photos.forEach {
                    println("Photo: ${it.bitmap} ${photos.size}")
                }
                GridImage(
                    gridItem = photos[0],
                    contentDesc = getString(FirstPictureContentDescription, userName),
                    onClick = { onImageClicked(0) },
                    modifier = Modifier.weight(2f)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(spacingAnim),
                    modifier = Modifier.weight(1f)
                ) {
                    GridImage(
                        gridItem = photos[1],
                        contentDesc = getString(SecondPictureContentDescription, userName),
                        onClick = { onImageClicked(1) },
                        modifier = Modifier.weight(1f)
                    )
                    GridImage(
                        gridItem = photos[2],
                        contentDesc = getString(ThirdPictureContentDescription, userName),
                        onClick = { onImageClicked(2) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(spacingAnim),
                modifier = Modifier.weight(1f)
            ) {
                GridImage(
                    gridItem = photos[3],
                    contentDesc = getString(ForthPictureContentDescription, userName),
                    onClick = { onImageClicked(3) },
                    modifier = Modifier.weight(1f)
                )

                GridImage(
                    gridItem = photos[4],
                    contentDesc = getString(FifthPictureContentDescription, userName),
                    onClick = { onImageClicked(4) },
                    modifier = Modifier.weight(1f)
                )

                GridImage(
                    gridItem = photos[5],
                    contentDesc = getString(SixthPictureContentDescription, userName),
                    onClick = { onImageClicked(5) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}

@Composable
internal fun GridImage(
    gridItem: ProfilePhoto,
    contentDesc: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val interactionSource = remember { MutableInteractionSource() }
    var isClicked by remember { mutableStateOf(false) }

    val scaleState by animateFloatAsState(
        targetValue = if (isClicked) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
        ),
    )

    val colorState by animateColorAsState(
        targetValue = if (isClicked) {
            if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Gray
            }
        } else MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )

    LaunchedEffect(isClicked) {
        if (isClicked) {
            delay(100L)
            isClicked = false
        }
    }

    Surface(
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.fillMaxSize()
            .scale(scaleState)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick()
                    isClicked = true
                }
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (gridItem.bitmap != null) {
                // TODO uncomment this when we have images coming from network
//                KamelImage(
//                    resource = asyncPainterResource(gridItem.url ?: ""),
//                    contentDescription = contentDesc,
//                    contentScale = ContentScale.Crop,
//                    onLoading = { progress -> CircularProgressIndicator(progress) },
//                    animationSpec = tween(),
//                    onFailure = { NotFoundImage() }
//                )
                Image(
                    bitmap = rememberBitmapFromBytes(gridItem.bitmap!!.toByteArray())!!,
                    contentDescription = contentDesc,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = contentDesc,
                    tint = colorState,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    }
}
