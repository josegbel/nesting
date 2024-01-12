package com.ajlabs.forevely.feature.updateProfile.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajlabs.forevely.feature.updateProfile.ContentCard
import com.ajlabs.forevely.feature.updateProfile.MAX_BIO_LENGTH
import com.ajlabs.forevely.localization.Strings.UpdateProfile.MyBioSectionSubtitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.MyBioSectionTitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.NoBio
import com.ajlabs.forevely.localization.getString

@Composable
internal fun MyBioSection(
    isEditing: Boolean,
    bioStatus: String?,
    onTextFieldFocusGained: () -> Unit = {},
    onValueChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onFocusChanged { if (it.isFocused) onTextFieldFocusGained() }
    ) {
        Text(getString(MyBioSectionTitle), style = MaterialTheme.typography.h6)
        Spacer(Modifier.padding(4.dp))
        Text(getString(MyBioSectionSubtitle))
        Spacer(Modifier.padding(8.dp))
        ContentCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {

                val focusManager = LocalFocusManager.current

                if (!isEditing) focusManager.clearFocus()

                BasicTextField(
                    value = bioStatus ?: "",
                    onValueChange = {
                        if (it.length <= MAX_BIO_LENGTH) onValueChanged(it)
                    },
                    textStyle = TextStyle(fontSize = 16.sp),
                    decorationBox = { innerTextField ->
                        if (bioStatus.isNullOrEmpty()) {
                            Text(
                                text = getString(NoBio),
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.consumeWindowInsets(WindowInsets.ime)
                )
            }
        }
    }
}
