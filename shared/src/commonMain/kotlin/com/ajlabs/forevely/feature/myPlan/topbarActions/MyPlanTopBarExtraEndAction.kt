package com.ajlabs.forevely.feature.myPlan.topbarActions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
internal fun MyPlanTopBarExtraEndAction(
    onClick: () -> Unit,
    contentDescription: String = "Show QR Code",
) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        imageVector = Icons.Default.QrCode,
        contentDescription = contentDescription,
        tint = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    )
}
