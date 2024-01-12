package com.ajlabs.forevely.util

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

data class WindowInfo(val width: Dp, val height: Dp, val minDimen: Dp? = null, val maxDimen: Dp? = null)

val LocalWindow = compositionLocalOf { WindowInfo(Dp.Unspecified, Dp.Unspecified) }
