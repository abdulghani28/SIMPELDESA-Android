package com.cvindosistem.simpeldesa.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText

@Composable
fun SuratSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    AppCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TitleMediumText(
                text = title,
            )
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun DataRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(0.4f)) {
            BodyMediumText(
                text = "$label:"
            )
        }
        Column(modifier = Modifier.weight(0.4f)) {
            BodyMediumText(
                text = value,
                fontWeight = FontWeight.Medium
            )
        }
    }
}