package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun <T> RecordsScreen(
    title: String,
    records: List<T>,
    navController: NavController,
    getDate: (T) -> String,
    content: @Composable (T) -> Unit
) {
    var selectedYear by remember { mutableStateOf("2024") }
    val years = listOf("2024", "2023", "2022")

    Scaffold(
        topBar = {
            AppTopBar(
                title = title,
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceBright)
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Year Dropdown menggunakan DropdownField
                DropdownField(
                    label = "Tahun",
                    value = selectedYear,
                    onValueChange = { selectedYear = it },
                    options = years,
                    isError = false,
                    errorMessage = null,
                    onDropdownExpanded = { /* Optional callback jika diperlukan */ }
                )
            }
            // Records List
            items(records) { record ->
                RecordCard(
                    record = record,
                    date = getDate(record),
                    modifier = Modifier.fillMaxWidth()
                ) { recordData ->
                    content(recordData)
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun <T> RecordCard(
    record: T,
    date: String,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    AppCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Date Header
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Record Content
            content(record)
        }
    }
}

// Info Row Component
@Composable
fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Two Column Info Row
@Composable
fun TwoColumnInfoRow(
    leftLabel: String,
    leftValue: String,
    rightLabel: String,
    rightValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoRow(
            label = leftLabel,
            value = leftValue,
            modifier = Modifier.weight(1f)
        )
        InfoRow(
            label = rightLabel,
            value = rightValue,
            modifier = Modifier.weight(1f)
        )
    }
}