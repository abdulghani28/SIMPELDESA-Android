package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FormSectionList(
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.surfaceBright,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        content()

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}