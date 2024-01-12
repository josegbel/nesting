package com.ajlabs.forevely.matcherCards

import androidx.compose.runtime.compositionLocalOf

data class ComposeWindow(val width: Int = 0, val height: Int = 0)

val LocalComposeWindow = compositionLocalOf { ComposeWindow() }
