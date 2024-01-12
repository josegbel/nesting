package com.ajlabs.forevely.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ajlabs.forevely.components.BottomBar
import com.ajlabs.forevely.feature.router.RouterScreen

@Preview
@Composable
private fun BottomBarPreview() {
    MaterialTheme(lightColors()) {
        BottomBar(
            routes = listOf(
                RouterScreen.MyPlan,
                RouterScreen.Profile,
                RouterScreen.Matcher,
                RouterScreen.Likes,
                RouterScreen.Chat,
            ),
            currentRoute = RouterScreen.Matcher,
            onDestinationClick = {},
            show = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
