package com.cvindosistem.simpeldesa.core.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AppDivider() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    )
}