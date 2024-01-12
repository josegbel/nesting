package com.ajlabs.forevely.feature.matcher.topbarActions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.BarTitleStyledHeader

@Composable
internal fun MatcherTopBarCenterAction(
    modifier: Modifier = Modifier,
    matcherProgress: Float,
    headerText: String
) {
    val progress by animateFloatAsState(matcherProgress)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        BarTitleStyledHeader(headerText)
        MatcherProgress(
            progress = progress,
        )
    }
}

@Composable
private fun MatcherProgress(
    progress: Float,
) {
    LinearProgressIndicator(
        progress = progress,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .width(200.dp)
            .height(2.dp),
    )
}
