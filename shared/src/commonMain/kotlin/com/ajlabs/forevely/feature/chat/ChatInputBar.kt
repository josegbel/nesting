package com.ajlabs.forevely.feature.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.components.IconButton

@Composable
internal fun ChatInputBar(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onAddAttachmentClick: () -> Unit,
) {
    var text by rememberSaveable { mutableStateOf(value) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
        ) {
            IconButton(
                icon = Icons.Outlined.AddCircle,
                contentDescription = "Add attachment", // TODO: move this to Strings
                onClick = onAddAttachmentClick
            )
            ChatInput(
                value = text,
                onValueChange = {
                    onValueChange(it)
                    text = it
                },
                onSend = {
                    onSend()
                    text = ""
                },
                modifier = Modifier.weight(1f)
            )
            IconButton(
                icon = Icons.Outlined.Send,
                contentDescription = "Send", // TODO: move this to Strings
                onClick = {
                    onSend()
                    text = ""
                }
            )
        }
    }
}

@Composable
internal fun ChatInput(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    bgColor: Color = MaterialTheme.colors.surface,
) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(percent = 50),
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Type your text here") }, // TODO: move this to Strings
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                imeAction = androidx.compose.ui.text.input.ImeAction.Send,
            ),
            keyboardActions = KeyboardActions(
                onSend = { onSend() },
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.onSurface,
                placeholderColor = MaterialTheme.colors.background,
            ),
            trailingIcon = {},
            modifier = modifier,
        )
    }
}
