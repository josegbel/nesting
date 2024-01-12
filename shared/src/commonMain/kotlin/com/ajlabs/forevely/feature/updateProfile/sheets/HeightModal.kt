package com.ajlabs.forevely.feature.updateProfile.sheets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.SimpleTopBar
import com.ajlabs.forevely.localization.Strings.Save
import com.ajlabs.forevely.localization.getString
import kotlinx.coroutines.launch

const val MIN_HEIGHT = 90f
const val MAX_HEIGHT = 220f

@Composable
internal fun <T : Enum<T>> HeightModal(
    onCloseClicked: () -> Unit,
    onSaveClicked: suspend () -> Unit,
    onValueChanged: (Float) -> Unit,
    height: Int,
    headerText: String,
    subtitle: String,
) {
    val scope = rememberCoroutineScope()

    Surface(color = MaterialTheme.colors.background) {
        Column {
            SimpleTopBar(
                headerText = headerText,
                onBackClicked = onCloseClicked
            )
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp, start = 16.dp, end = 16.dp)
                        .animateContentSize(),
                ) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    item {
                        Text(
                            text = subtitle,
                            fontWeight = W600,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        Slider(
                            value = height.toFloat(),
                            onValueChange = onValueChanged,
                            valueRange = MIN_HEIGHT..MAX_HEIGHT,
                            steps = (MAX_HEIGHT - MIN_HEIGHT).toInt() + 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }

                    item {
                        Text(
                            text = "${height}cm",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
                Button(
                    onClick = {
                        scope.launch {
                            onSaveClicked()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(BottomCenter),
                ) {
                    Text(
                        text = getString(Save),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
            }
        }
    }
}
