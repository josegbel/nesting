package com.ajlabs.forevely.feature.updateProfile.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajlabs.forevely.data.type.OpenQuestion
import com.ajlabs.forevely.feature.updateProfile.ContentCard
import com.ajlabs.forevely.feature.updateProfile.sheets.stringKey
import com.ajlabs.forevely.localization.Strings.UpdateProfile.NoOpeningQuestion
import com.ajlabs.forevely.localization.Strings.UpdateProfile.OpeningQuestionSectionSubtitle
import com.ajlabs.forevely.localization.Strings.UpdateProfile.OpeningQuestionSectionTitle
import com.ajlabs.forevely.localization.getString

@Composable
fun OpeningQuestionSection(openingQuestion: OpenQuestion?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(getString(OpeningQuestionSectionTitle), style = MaterialTheme.typography.h6)
        Spacer(Modifier.padding(4.dp))
        Text(getString(OpeningQuestionSectionSubtitle))
        Spacer(Modifier.padding(8.dp))
        ContentCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (openingQuestion == null) {
                    Text(getString(NoOpeningQuestion))
                    Spacer(Modifier.padding(8.dp))
                } else {
                    Text(getString(openingQuestion.stringKey()))
                }
            }
        }
    }
}
