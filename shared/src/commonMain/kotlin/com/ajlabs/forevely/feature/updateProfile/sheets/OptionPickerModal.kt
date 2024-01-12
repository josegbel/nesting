package com.ajlabs.forevely.feature.updateProfile.sheets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.SimpleTopBar
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.Strings.Save
import com.ajlabs.forevely.localization.getString
import kotlin.reflect.KFunction1
import kotlinx.coroutines.launch

@Composable
internal fun <T : Enum<T>> OptionPickerModal(
    onCloseClicked: () -> Unit,
    onSaveClicked: suspend () -> Unit,
    onItemClicked: (T) -> Unit,
    headerText: String,
    subtitle: String,
    enumValues: Array<T>,
    stringMapper: KFunction1<T, Strings>,
    stateField: T?,
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

                    enumValues.forEach { value ->
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { onItemClicked(value) }) {
                                Text(
                                    text = getString(stringMapper(value)),
                                    modifier = Modifier.weight(1f),
                                )
                                RadioButton(
                                    selected = stateField == value,
                                    onClick = { onItemClicked(value) },
                                )
                            }
                        }
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
