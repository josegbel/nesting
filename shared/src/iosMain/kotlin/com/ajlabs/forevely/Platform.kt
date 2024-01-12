package com.ajlabs.forevely

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.ajlabs.forevely.feature.RootContent
import com.ajlabs.forevely.matcherCards.ComposeWindow
import com.ajlabs.forevely.matcherCards.LocalComposeWindow
import com.ajlabs.forevely.theme.AppTheme
import com.ajlabs.forevely.theme.safePaddingValues
import com.ajlabs.forevely.util.LocalWindow
import com.ajlabs.forevely.util.WindowInfo
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import org.koin.compose.KoinContext
import org.koin.dsl.module
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow

@OptIn(ExperimentalForeignApi::class)
@Suppress("unused", "FunctionNaming", "FunctionName")
fun MainViewController(window: UIWindow): UIViewController {
    val koinApplication = initKoin()

    val uiViewController = ComposeUIViewController {
        LaunchedEffect(window.safeAreaInsets) {
            window.safeAreaInsets.useContents {
                safePaddingValues = PaddingValues(
                    top = this.top.dp,
                    bottom = this.bottom.dp,
                    start = this.left.dp,
                    end = this.right.dp,
                )
            }
        }

        val rememberedWindowInfo by remember(window) {
            val windowInfo = window.frame.useContents {
                WindowInfo(this.size.width.dp, this.size.height.dp)
            }
            mutableStateOf(windowInfo)
        }

        val composeWindow by remember(window) {
            val windowInfo = window.frame.useContents {
                ComposeWindow(this.size.width.toInt(), this.size.height.toInt())
            }
            mutableStateOf(windowInfo)
        }

        KoinContext(koinApplication.koin) {
            CompositionLocalProvider(
                LocalComposeWindow provides composeWindow,
                LocalWindow provides rememberedWindowInfo,
            ) {
                AppTheme {
                    RootContent()
                }
            }
        }
    }

    koinApplication.koin.loadModules(
        listOf(
            module { single<() -> UIViewController> { { uiViewController } } }
        )
    )

    return uiViewController
}
