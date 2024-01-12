package com.ajlabs.forevely.feature.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun QRCodeModal(
    onCloseClicked: suspend () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        color = MaterialTheme.colors.primary,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(
                text = "QRCode",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.Center),
            )
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            scope.launch {
                                onCloseClicked()
                            }
                        },
                    ),
            )
        }
    }
}
