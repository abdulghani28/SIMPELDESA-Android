package com.cvindosistem.simpeldesa.presentation.screens.auth.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun RegisterLink() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Belum memiliki akun?",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF6B7280)
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        TextButton(
            onClick = { /* Handle register navigation */ },
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "DAFTAR SEKARANG",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF3B82F6),
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}