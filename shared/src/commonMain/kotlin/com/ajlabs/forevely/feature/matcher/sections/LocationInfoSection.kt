package com.ajlabs.forevely.feature.matcher.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.Map
import com.ajlabs.forevely.feature.debug.defaultLocation
import com.ajlabs.forevely.feature.debug.model.LatLng
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString

@Composable
internal fun LocationInfoSection(
    modifier: Modifier = Modifier,
    userName: String?,
    city: String?,
    state: String?,
    country: String?,
    isLocationEnabled: Boolean,
    latLng: LatLng?,
    distance: Int?,
    onCLick: () -> Unit,
    bgColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onSurface,
    chipColor: Color = MaterialTheme.colors.primary,
) {
    Surface(
        color = bgColor,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
                userName?.let {
                    Text(
                        text = it,
                        color = contentColor,
                    )
                }
            }
            Column {
                val cityName = "$city, " ?: ""
                val stateName = state ?: ""
                Text(
                    text = cityName + stateName,
                    color = contentColor,
                    fontWeight = MaterialTheme.typography.h6.fontWeight,
                )
                distance?.let {
                    Text(
                        text = cityName + stateName + "\n" + getString(Strings.Matcher.LocationMilesAway, it),
                        color = contentColor,
                        fontWeight = MaterialTheme.typography.h6.fontWeight,
                    )
                }
            }

            country?.let {
                val livesInText = getString(Strings.Matcher.LivesIn, it)
                Chip(
                    onClick = onCLick,
                    text = livesInText,
                    icon = Icons.Default.LocationOn,
                    contentDescription = livesInText,
                    chipColor = chipColor,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }

            AnimatedVisibility(latLng != null && isLocationEnabled) {
                Surface(
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(200.dp),
                ) {
                    Map(
                        location = LatLng(
                            latitude = latLng?.latitude ?: defaultLocation.latitude,
                            longitude = latLng?.longitude ?: defaultLocation.longitude,
                        ),
                        marketTitle = userName,
                        isInteractionEnabled = false,
                        onMarkerClick = {},
                        onPositionChange = {},
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
