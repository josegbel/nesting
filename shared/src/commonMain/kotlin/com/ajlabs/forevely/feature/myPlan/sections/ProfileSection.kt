package com.ajlabs.forevely.feature.myPlan.sections

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.AnimatedCircularProgressIndicator
import com.ajlabs.forevely.components.NameAgeVerifiedRow
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
internal fun ProfileSection(
    modifier: Modifier = Modifier,
    userName: String?,
    age: Int?,
    imageUrl: String?,
    profileCompletion: Int,
    completedProfileText: String,
    notCompletedProfileText: String,
    onCompleteProfileClick: () -> Unit,
) {
    val profileButtonText = if (profileCompletion < 100) {
        notCompletedProfileText
    } else {
        completedProfileText
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        ProfileImage(
            imageUrl = imageUrl,
            profileCompletion = profileCompletion,
        )
        NameAgeVerifiedRow(
            userName = userName,
            age = age,
            isVerified = true,
            modifier = Modifier
                .offset(y = (-8).dp),
        )
        CompleteProfileButton(
            text = profileButtonText,
            onClick = onCompleteProfileClick,
            modifier = Modifier
                .offset(y = (userName?.let { -4 } ?: 4).dp),
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ProfileImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    profileCompletion: Int,
    imageSize: Dp = 120.dp,
    strokeWidth: Dp = 6.dp,
) {
    Box(
        modifier = modifier,
    ) {
        AnimatedCircularProgressIndicator(
            currentValue = profileCompletion,
            maxValue = 100,
            progressBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f),
            progressIndicatorColor = MaterialTheme.colors.primary,
            completedColor = MaterialTheme.colors.primaryVariant,
            diameter = imageSize + strokeWidth,
            modifier = Modifier
                .padding(bottom = 16.dp), // This moves indicator up/down
        ) {
            Surface(
                shape = CircleShape,
                modifier = modifier
                    .size(imageSize)
                    .clip(CircleShape),
            ) {
                if (imageUrl?.isNotEmpty() == true && imageUrl.isNotEmpty()) {
                    KamelImage(
                        resource = asyncPainterResource(imageUrl),
                        contentDescription = getString(Strings.MyPlan.UsersProfileImageCircle),
                        contentScale = ContentScale.Crop,
                        onLoading = { progress -> CircularProgressIndicator(progress) },
                        animationSpec = tween(),
                        modifier = Modifier,
                    )
                } else {
                    Surface(color = MaterialTheme.colors.secondary) {
                        Icon(
                            imageVector = Icons.Default.BrokenImage,
                            contentDescription = "No image",
                            tint = MaterialTheme.colors.onSecondary,
                            modifier = Modifier
                                .size(12.dp),
                        )
                    }
                }
            }
        }
        Surface(
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Surface(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.padding(4.dp),
            ) {
                Text(
                    text = "$profileCompletion%",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(
                            vertical = 2.dp,
                            horizontal = 12.dp,
                        ),
                )
            }
        }
    }
}

@Composable
private fun CompleteProfileButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    bgColor: Color = MaterialTheme.colors.surface,
    textColor: Color = MaterialTheme.colors.onSurface,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(percent = 50),
        modifier = modifier
            .semantics {
                this.contentDescription = text
                this.role = Role.Button
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
        )
    }
}
