package com.cvindosistem.simpeldesa.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.ui.theme.StatusDiproses
import com.cvindosistem.simpeldesa.ui.theme.StatusDitolak
import com.cvindosistem.simpeldesa.ui.theme.StatusMenunggu
import com.cvindosistem.simpeldesa.ui.theme.StatusSelesai
import com.cvindosistem.simpeldesa.ui.theme.TextPrimary
import com.cvindosistem.simpeldesa.ui.theme.TextSecondary
import com.cvindosistem.simpeldesa.ui.theme.WorkSans

@Composable
fun StatusChip(
    status: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status.lowercase()) {
        "menunggu" -> StatusMenunggu
        "diproses" -> StatusDiproses
        "selesai" -> StatusSelesai
        "dibatalkan" -> StatusDitolak
        else -> StatusMenunggu
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = backgroundColor,
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontFamily = WorkSans
        )
    }
}

@Composable
fun LetterItem(
    status: String,
    title: String,
    description: String,
    date: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusChip(status = status)
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontFamily = WorkSans
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontFamily = WorkSans,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                fontFamily = WorkSans
            )
        }
    }
}