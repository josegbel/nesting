package com.ajlabs.forevely.util

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.ajlabs.forevely.theme.AppTheme

@Composable
fun Window.SetColors() {
    AppTheme {
        statusBarColor = MaterialTheme.colors.background.toArgb()
        navigationBarColor = MaterialTheme.colors.background.toArgb()

        SetStatusBarDarkIcons()
        SetNavigationBarDarkIcons()

        val wic = WindowCompat.getInsetsController(this, this.decorView)
        wic.isAppearanceLightStatusBars = !isSystemInDarkTheme()
    }
}

@Suppress("DEPRECATION")
@Composable
private fun Window.SetStatusBarDarkIcons() {
    when {
        Build.VERSION_CODES.R <= Build.VERSION.SDK_INT -> insetsController?.setSystemBarsAppearance(
            if (!isSystemInDarkTheme()) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        )

        else -> decorView.systemUiVisibility = if (!isSystemInDarkTheme()) {
            decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}

@Suppress("DEPRECATION")
@Composable
private fun Window.SetNavigationBarDarkIcons() {
    when {
        Build.VERSION_CODES.R <= Build.VERSION.SDK_INT -> insetsController?.setSystemBarsAppearance(
            if (!isSystemInDarkTheme()) WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
        )

        else -> decorView.systemUiVisibility = if (!isSystemInDarkTheme()) {
            decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
    }
}
