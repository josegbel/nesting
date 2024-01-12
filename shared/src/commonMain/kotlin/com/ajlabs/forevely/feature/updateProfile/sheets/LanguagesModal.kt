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
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.SimpleTopBar
import com.ajlabs.forevely.data.type.Language
import com.ajlabs.forevely.feature.updateProfile.UpdateProfileContract.State
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.Strings.Save
import com.ajlabs.forevely.localization.getString
import kotlinx.coroutines.launch

// FIXME: For now using LocationModal and MyBasicsModal whicha are the same. Remove location when get there
@Composable
internal fun LanguagesModal(
    onCloseClicked: suspend () -> Unit,
    onSaveClicked: suspend (List<Language>) -> Unit,
    state: State,
    onLanguageChecked: (language: Language, isChecked: Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Surface(color = MaterialTheme.colors.background) {
        Column {
            SimpleTopBar(
                headerText = getString(Strings.User.Languages.ModalTitle),
                onBackClicked = { scope.launch { onCloseClicked() } }
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
                            text = getString(Strings.User.Languages.ModalSubtitle),
                            fontWeight = FontWeight(600),
                        )
                    }
                    Language.values().sorted().forEach { language ->
                        item { Divider() }
                        item {
                            state.uiUser?.profile?.aboutMe?.languages?.let { languages ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                        .clickable { onLanguageChecked(language, !languages.contains(language)) }
                                ) {
                                    Text(text = language.name, modifier = Modifier.weight(1f))
                                    Checkbox(
                                        checked = languages.contains(language),
                                        onCheckedChange = null,
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                    )
                                }
                            }

                        }
                    }


                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
                Button(
                    onClick = {
                        scope.launch {
                            onSaveClicked(state.uiUser?.profile?.aboutMe?.languages ?: emptyList())
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
