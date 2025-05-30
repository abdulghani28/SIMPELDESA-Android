package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppContainer
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.StatusSurat
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSaya
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel
import com.zynksoftware.documentscanner.model.DocumentScannerErrorModel.ErrorMessage
import java.text.SimpleDateFormat
import java.util.*

@Composable
internal fun SuratSayaContent(
    modifier: Modifier = Modifier,
    viewModel: SuratSayaViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val showFilterSheet = viewModel.showFilterSheet

    // Handle events
    LaunchedEffect(Unit) {
        viewModel.suratEvent.collect { event ->
            when (event) {
                is SuratSayaViewModel.SuratSayaEvent.DataLoaded -> {
                    // Handle data loaded if needed
                }
                is SuratSayaViewModel.SuratSayaEvent.ShowFilter -> {
                    // Handle show filter dialog
                }
                is SuratSayaViewModel.SuratSayaEvent.Error -> {
                    // Handle error
                }
            }
        }
    }

    AppContainer(
        background = MaterialTheme.colorScheme.surfaceBright,
        modifier = modifier
    ) {
        AppSearchBarAndFilter(
            placeholder = "Cari Surat",
            value = viewModel.searchQuery,
            onValueSearch = { query ->
                viewModel.onSearchValueChange(query)
            },
            onFilterClick = {
                viewModel.onFilterClick()
            },
            showFilter = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        when {
            isLoading && uiState.value.suratList.isEmpty() -> {
                // Show loading for initial load
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null && uiState.value.suratList.isEmpty() -> {
                // Show error for initial load
            }
            uiState.value.suratList.isEmpty() && uiState.value.isDataLoaded -> {
                // Show empty state
            }
            else -> {
                SuratListSection(
                    suratList = uiState.value.suratList,
                    isLoading = isLoading,
                    onLoadMore = { viewModel.loadMoreData() }
                )
            }
        }
    }

    if (showFilterSheet) {
        FilterBottomSheet(
            onDismiss = { viewModel.onDismissFilterSheet() },
            onApplyFilter = { filterData ->
                viewModel.applyFilter(filterData)
            },
            currentFilter = viewModel.getCurrentFilter()
        )
    }
}

@Composable
private fun SuratListSection(
    suratList: List<SuratSaya>,
    isLoading: Boolean,
    onLoadMore: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(suratList) { surat ->
            SuratSayaCard(surat = surat)
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Load more trigger
        item {
            LaunchedEffect(Unit) {
                onLoadMore()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SuratSayaCard(
    surat: SuratSaya
) {
    AppCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                StatusChip(
                    status = surat.status,
                    modifier = Modifier
                )

                BodySmallText(
                    text = surat.tanggal
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TitleMediumText(
                text = surat.judul
            )

            Spacer(modifier = Modifier.height(6.dp))

            BodySmallText(
                text = surat.deskripsi
            )

            // Optional: Show additional info from API
            if (surat.nama.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(
                    text = "Pengaju: ${surat.nama}",
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    status: StatusSurat,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        StatusSurat.MENUNGGU -> Color(0xFFE3F2FD)
        StatusSurat.DIPROSES -> Color(0xFFFFF3E0)
        StatusSurat.SELESAI -> Color(0xFFE8F5E8)
        StatusSurat.DITOLAK -> Color(0xFFFFEBEE)
    }

    val textColor = when (status) {
        StatusSurat.MENUNGGU -> Color(0xFF1976D2)
        StatusSurat.DIPROSES -> Color(0xFFFF8F00)
        StatusSurat.SELESAI -> Color(0xFF388E3C)
        StatusSurat.DITOLAK -> Color(0xFFD32F2F)
    }

    val statusText = when (status) {
        StatusSurat.MENUNGGU -> "Menunggu"
        StatusSurat.DIPROSES -> "Diproses"
        StatusSurat.SELESAI -> "Selesai"
        StatusSurat.DITOLAK -> "Ditolak"
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismiss: () -> Unit,
    onApplyFilter: (FilterData) -> Unit,
    currentFilter: FilterData = FilterData()
) {
    var selectedStatuses by remember { mutableStateOf(currentFilter.statuses) }
    var startDate by remember { mutableStateOf(currentFilter.startDate) }
    var endDate by remember { mutableStateOf(currentFilter.endDate) }
    var selectedJenisSurat by remember { mutableStateOf(currentFilter.jenisSurat) }

    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = {
            Surface(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(width = 32.dp, height = 4.dp),
                shape = RoundedCornerShape(2.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            ) {}
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Filter",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Status Surat Section
            StatusSuratSection(
                selectedStatuses = selectedStatuses,
                onStatusChange = { status, isSelected ->
                    selectedStatuses = if (isSelected) {
                        selectedStatuses + status
                    } else {
                        selectedStatuses - status
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Date Range Section
            DateRangeSection(
                startDate = startDate,
                endDate = endDate,
                onStartDateChange = { startDate = it },
                onEndDateChange = { endDate = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Jenis Surat Section
            JenisSuratSection(
                selectedJenisSurat = selectedJenisSurat,
                onJenisSuratChange = { selectedJenisSurat = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextButton(
                    onClick = {
                        // Reset all filters
                        selectedStatuses = emptySet()
                        startDate = ""
                        endDate = ""
                        selectedJenisSurat = emptySet()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset Filter")
                }

                Button(
                    onClick = {
                        onApplyFilter(
                            FilterData(
                                statuses = selectedStatuses,
                                startDate = startDate,
                                endDate = endDate,
                                jenisSurat = selectedJenisSurat
                            )
                        )
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Terapkan")
                }
            }

            // Add bottom padding for navigation bar
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun StatusSuratSection(
    selectedStatuses: Set<String>,
    onStatusChange: (String, Boolean) -> Unit
) {
    Column {
        Text(
            text = "Status Surat",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(getStatusList()) { status ->
                FilterChip(
                    selected = selectedStatuses.contains(status.value),
                    onClick = {
                        onStatusChange(status.value, !selectedStatuses.contains(status.value))
                    },
                    label = {
                        Text(
                            text = status.label,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    leadingIcon = if (selectedStatuses.contains(status.value)) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = status.backgroundColor,
                        selectedLabelColor = status.textColor
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangeSection(
    startDate: String,
    endDate: String,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mulai Dari Tanggal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = startDate,
                    onValueChange = { },
                    placeholder = {
                        Text(
                            text = "Tgl / Bln / Thn",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showStartDatePicker = true },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date"
                        )
                    }
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Sampai Dengan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = endDate,
                    onValueChange = { },
                    placeholder = {
                        Text(
                            text = "Tgl / Bln / Thn",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showEndDatePicker = true },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date"
                        )
                    }
                )
            }
        }
    }

    // Date Pickers
    if (showStartDatePicker) {
        DatePickerDialog(
            onDateSelected = { date ->
                onStartDateChange(date)
                showStartDatePicker = false
            },
            onDismiss = { showStartDatePicker = false }
        )
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDateSelected = { date ->
                onEndDateChange(date)
                showEndDatePicker = false
            },
            onDismiss = { showEndDatePicker = false }
        )
    }
}

@Composable
private fun JenisSuratSection(
    selectedJenisSurat: Set<String>,
    onJenisSuratChange: (Set<String>) -> Unit
) {
    var expandedSections by remember { mutableStateOf(setOf<String>()) }

    Column {
        Text(
            text = "Jenis Surat",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Surat Keterangan (Dropdown)
        DropdownSection(
            title = "Surat Keterangan",
            isExpanded = false,
            onClick = { /* Handle dropdown click */ }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Surat Pengantar (Dropdown)
        DropdownSection(
            title = "Surat Pengantar",
            isExpanded = false,
            onClick = { /* Handle dropdown click */ }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Surat Rekomendasi (Expandable)
        ExpandableSection(
            title = "Surat Rekomendasi",
            isExpanded = expandedSections.contains("rekomendasi"),
            onToggle = {
                expandedSections = if (expandedSections.contains("rekomendasi")) {
                    expandedSections - "rekomendasi"
                } else {
                    expandedSections + "rekomendasi"
                }
            }
        ) {
            CheckboxItem(
                text = "Surat Rekomendasi Keramaian",
                isChecked = selectedJenisSurat.contains("rekomendasi_keramaian"),
                onCheckedChange = { isChecked ->
                    val newSet = if (isChecked) {
                        selectedJenisSurat + "rekomendasi_keramaian"
                    } else {
                        selectedJenisSurat - "rekomendasi_keramaian"
                    }
                    onJenisSuratChange(newSet)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Surat Lainnya (Expandable)
        ExpandableSection(
            title = "Surat Lainnya",
            isExpanded = expandedSections.contains("lainnya"),
            onToggle = {
                expandedSections = if (expandedSections.contains("lainnya")) {
                    expandedSections - "lainnya"
                } else {
                    expandedSections + "lainnya"
                }
            }
        ) {
            CheckboxItem(
                text = "Surat Kuasa",
                isChecked = selectedJenisSurat.contains("kuasa"),
                onCheckedChange = { isChecked ->
                    val newSet = if (isChecked) {
                        selectedJenisSurat + "kuasa"
                    } else {
                        selectedJenisSurat - "kuasa"
                    }
                    onJenisSuratChange(newSet)
                }
            )

            CheckboxItem(
                text = "Surat Tugas",
                isChecked = selectedJenisSurat.contains("tugas"),
                onCheckedChange = { isChecked ->
                    val newSet = if (isChecked) {
                        selectedJenisSurat + "tugas"
                    } else {
                        selectedJenisSurat - "tugas"
                    }
                    onJenisSuratChange(newSet)
                }
            )
        }
    }
}

@Composable
private fun DropdownSection(
    title: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand"
            )
        }
    }
}

@Composable
private fun ExpandableSection(
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium
                )

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }

            if (isExpanded) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun CheckboxItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Date(millis)
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        onDateSelected(formatter.format(date))
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

// Data classes
data class FilterData(
    val statuses: Set<String> = emptySet(),
    val startDate: String = "",
    val endDate: String = "",
    val jenisSurat: Set<String> = emptySet()
)

data class StatusItem(
    val label: String,
    val value: String,
    val backgroundColor: Color,
    val textColor: Color
)

// Helper functions
private fun getStatusList(): List<StatusItem> {
    return listOf(
        StatusItem(
            label = "Menunggu",
            value = "menunggu",
            backgroundColor = Color(0xFFE3F2FD),
            textColor = Color(0xFF1976D2)
        ),
        StatusItem(
            label = "Diproses",
            value = "diproses",
            backgroundColor = Color(0xFFFFF3E0),
            textColor = Color(0xFFFF8F00)
        ),
        StatusItem(
            label = "Selesai",
            value = "selesai",
            backgroundColor = Color(0xFFE8F5E8),
            textColor = Color(0xFF388E3C)
        ),
        StatusItem(
            label = "Ditolak",
            value = "ditolak",
            backgroundColor = Color(0xFFFFEBEE),
            textColor = Color(0xFFD32F2F)
        )
    )
}