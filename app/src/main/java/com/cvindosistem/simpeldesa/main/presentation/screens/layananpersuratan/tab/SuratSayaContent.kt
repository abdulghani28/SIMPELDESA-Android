package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.StatusSurat
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSaya
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel

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
                    suratList = viewModel.filteredSuratList.collectAsState().value,
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
        StatusSurat.DIBATALKAN -> Color(0xFFFFEBEE)
    }

    val textColor = when (status) {
        StatusSurat.MENUNGGU -> Color(0xFF1976D2)
        StatusSurat.DIPROSES -> Color(0xFFFF8F00)
        StatusSurat.SELESAI -> Color(0xFF388E3C)
        StatusSurat.DIBATALKAN -> Color(0xFFD32F2F)
    }

    val statusText = when (status) {
        StatusSurat.MENUNGGU -> "Menunggu"
        StatusSurat.DIPROSES -> "Diproses"
        StatusSurat.SELESAI -> "Selesai"
        StatusSurat.DIBATALKAN -> "Dibatalkan"
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

    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

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
private fun DateRangeSection(
    startDate: String,
    endDate: String,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            DatePickerField(
                label = "Tanggal Mulai",
                value = startDate,
                onValueChange = onStartDateChange,
                isError = false,
                errorMessage = null,
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            DatePickerField(
                label = "Tanggal Selesai",
                value = endDate,
                onValueChange = onEndDateChange,
                isError = false,
                errorMessage = null,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
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

        // Changed from LazyRow to FlowRow for wrapping
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            getStatusList().forEach { status ->
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


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JenisSuratSection(
    selectedJenisSurat: Set<String>,
    onJenisSuratChange: (Set<String>) -> Unit
) {
    val jenisSuratOptions = listOf(
        "Surat Keterangan Domisili",
        "Surat Keterangan Domisili Perusahaan",
        "Surat Keterangan Tidak Mampu",
        "Surat Keterangan Kelahiran",
        "Surat Keterangan Kematian",
        "Surat Keterangan Usaha",
        "Surat Keterangan Bepergian",
        "Surat Keterangan Izin Tidak Masuk Kerja",
        "Surat Keterangan Penghasilan",
        "Surat Keterangan Status Perkawinan",
        "Surat Keterangan Resi KTP Sementara",
        "Surat Keterangan Janda Duda",
        "Surat Keterangan Beda Identitas",
        "Surat Keterangan Ghaib",
        "Surat Pengantar Catatan Kepolisian",
        "Surat Pengantar Kehilangan",
        "Surat Pengantar Pernikahan",
        "Surat Rekomendasi Izin Keramaian",
        "Surat Kuasa",
        "Surat Tugas",
        "Surat Pengantar Pindah Domisili"
    )

    var selectedSurat by remember { mutableStateOf("") }

    Column {
        DropdownField(
            label = "Jenis Surat",
            value = selectedSurat,
            onValueChange = { selected ->
                selectedSurat = selected
                val newSet = if (selectedJenisSurat.contains(selected)) {
                    selectedJenisSurat - selected
                } else {
                    selectedJenisSurat + selected
                }
                onJenisSuratChange(newSet)
            },
            options = jenisSuratOptions,
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Display selected jenis surat as chips
        if (selectedJenisSurat.isNotEmpty()) {
            Text(
                text = "Jenis Surat Terpilih:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                selectedJenisSurat.forEach { jenis ->
                    AssistChip(
                        onClick = {
                            onJenisSuratChange(selectedJenisSurat - jenis)
                        },
                        label = {
                            Text(
                                text = jenis,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
        }
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
            label = "Dibatalkan",
            value = "dibatalkan",
            backgroundColor = Color(0xFFFFEBEE),
            textColor = Color(0xFFD32F2F)
        )
    )
}