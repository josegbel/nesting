package com.ajlabs.forevely.feature.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.ajlabs.forevely.feature.myPlan.OnBoardingContract
import com.ajlabs.forevely.feature.myPlan.OnBoardingViewModel
import com.ajlabs.forevely.localization.Strings
import com.ajlabs.forevely.localization.getString

@Composable
internal fun PhoneNumberContent(
    onError: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val vm = remember(scope) {
        OnBoardingViewModel(
            scope = scope,
            onError = onError,
        )
    }

    val state by vm.observeStates().collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopBar(
            onCloseClicked = onCloseClicked,
        )
        Header()
        PhoneNumberInput(
            value = state.phoneNumber,
            onValueChanged = { vm.trySend(OnBoardingContract.Inputs.SetPhoneNumber(it)) },
            onSendClicked = { vm.trySend(OnBoardingContract.Inputs.OnPhoneNumberSendClicked) },
        )
    }
}

@Composable
fun PhoneNumberInput(
    value: String,
    onValueChanged: (String) -> Unit,
    onSendClicked: () -> Unit,
) {
    Text(
        text = getString(Strings.OnBoarding.PhoneNumber.PhoneHeader),
        color = MaterialTheme.colors.onSurface,
    )
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Send,
        ),
        keyboardActions = KeyboardActions(
            onSend = { onSendClicked() }
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun Header() {
    Text(
        text = getString(Strings.OnBoarding.PhoneNumber.WhatsYourNumber),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h5,
    )
    Text(
        text = getString(Strings.OnBoarding.PhoneNumber.HeaderDescription),
        color = MaterialTheme.colors.onSurface,
    )
}

@Composable
private fun TopBar(
    onCloseClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = getString(Strings.CloseButton),
            modifier = Modifier.clickable(onClick = onCloseClicked)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
