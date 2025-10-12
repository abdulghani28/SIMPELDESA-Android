package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.domain.model.SuratListResult
import com.cvindosistem.simpeldesa.main.domain.usecases.GetSuratListUseCase
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.FilterData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SuratSayaViewModel(
    private val getSuratListUseCase: GetSuratListUseCase
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(SuratSayaUiState())
    val uiState = _uiState.asStateFlow()

    // Loading state
    var isLoading by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Search and filter states
    var searchQuery by mutableStateOf("")
        private set

    private var currentFilter = FilterData()

    // Show filter sheet state
    var showFilterSheet by mutableStateOf(false)
        private set

    // Filtered surat list
    val filteredSuratList: StateFlow<List<SuratSaya>> = combine(
        _uiState,
        snapshotFlow { searchQuery }
    ) { uiState, query ->
        if (query.isEmpty()) {
            uiState.suratList
        } else {
            uiState.suratList.filter { surat ->
                surat.judul.contains(query, ignoreCase = true) ||
                        surat.nama.contains(query, ignoreCase = true) ||
                        surat.nik.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Pagination
    private var currentPage = 1
    private val limit = 10
    private var canLoadMore = true

    // Events
    private val _suratEvent = MutableSharedFlow<SuratSayaEvent>()
    val suratEvent = _suratEvent.asSharedFlow()

    init {
        loadSuratList()
    }

    fun loadSuratList(refresh: Boolean = false) {
        viewModelScope.launch {
            if (refresh) {
                currentPage = 1
                canLoadMore = true
                _uiState.value = _uiState.value.copy(suratList = emptyList())
            }

            if (!canLoadMore && !refresh) return@launch

            isLoading = true
            errorMessage = null

            when (val result = getSuratListUseCase(
                page = currentPage,
                limit = limit,
                jenisSurat = if (currentFilter.jenisSurat.isNotEmpty())
                    currentFilter.jenisSurat.joinToString(",") else null,
                status = if (currentFilter.statuses.isNotEmpty())
                    currentFilter.statuses.joinToString(",") else null,
                startDate = currentFilter.startDate.ifEmpty { null },
                endDate = currentFilter.endDate.ifEmpty { null }
            )) {
                is SuratListResult.Success -> {
                    // Mapping data API ke model UI
                    val newSuratList = result.data.data?.map { data ->
                        SuratSaya(
                            id = data.id ?: "",
                            judul = data.jenis_surat ?: "",
                            deskripsi = getDescriptionByStatus(data.status ?: "", data.jenis_surat ?: ""),
                            tanggal = dateFormatterToApiFormat(data.created_at ?: ""),
                            status = mapStringToStatusSurat(data.status ?: ""),
                            nik = data.nik ?: "",
                            nama = data.nama ?: "",
                            diprosesOlehId = data.diproses_oleh_id ?: "",
                            disahkanOlehId = data.disahkan_oleh_id ?: ""
                        )
                    } ?: emptyList()

                    val currentList = if (refresh) emptyList() else _uiState.value.suratList
                    val updatedList = currentList + newSuratList

                    _uiState.value = _uiState.value.copy(
                        suratList = updatedList,
                        totalData = result.data.meta?.total ?: 0,
                        isDataLoaded = true
                    )

                    // Cek apakah masih bisa load lagi
                    canLoadMore = newSuratList.size == limit
                    if (canLoadMore) currentPage++

                    _suratEvent.emit(SuratSayaEvent.DataLoaded)
                }

                is SuratListResult.Error -> {
                    errorMessage = result.message
                    _suratEvent.emit(SuratSayaEvent.Error(result.message))
                }
            }

            isLoading = false
        }
    }

    fun loadMoreData() {
        if (canLoadMore && !isLoading) {
            loadSuratList()
        }
    }

    fun onSearchValueChange(query: String) {
        searchQuery = query
    }

    fun onFilterClick() {
        showFilterSheet = true
    }

    fun onDismissFilterSheet() {
        showFilterSheet = false
    }

    fun applyFilter(filterData: FilterData) {
        currentFilter = filterData
        showFilterSheet = false
        refreshData()
    }

    fun getCurrentFilter(): FilterData = currentFilter

    fun clearFilter() {
        currentFilter = FilterData()
        refreshData()
    }

    fun refreshData() {
        loadSuratList(refresh = true)
    }

    fun clearError() {
        errorMessage = null
    }

    // Helper: map string ke enum StatusSurat
    private fun mapStringToStatusSurat(status: String): StatusSurat {
        return when (status.lowercase()) {
            "menunggu", "pending" -> StatusSurat.MENUNGGU
            "diproses", "processing" -> StatusSurat.DIPROSES
            "selesai", "completed", "done" -> StatusSurat.SELESAI
            "dibatalkan", "rejected" -> StatusSurat.DIBATALKAN
            else -> StatusSurat.MENUNGGU
        }
    }

    // Helper: deskripsi berdasarkan status
    private fun getDescriptionByStatus(status: String, jenisSurat: String): String {
        return when (status.lowercase()) {
            "menunggu", "pending" ->
                "Surat $jenisSurat berhasil diajukan ke pihak desa dan akan segera diproses."
            "diproses", "processing" ->
                "Surat $jenisSurat yang Anda ajukan saat ini sedang dalam proses oleh pihak desa."
            "selesai", "completed", "done" ->
                "Surat $jenisSurat yang Anda ajukan sudah selesai diproses dan siap untuk diambil di kantor desa."
            "dibatalkan", "rejected" ->
                "Mohon maaf, surat $jenisSurat yang Anda ajukan telah dibatalkan oleh pihak desa."
            else ->
                "Surat $jenisSurat sedang dalam proses."
        }
    }

    sealed class SuratSayaEvent {
        data object DataLoaded : SuratSayaEvent()
        data object ShowFilter : SuratSayaEvent()
        data class Error(val message: String) : SuratSayaEvent()
    }
}

// UI State
data class SuratSayaUiState(
    val suratList: List<SuratSaya> = emptyList(),
    val totalData: Int = 0,
    val isDataLoaded: Boolean = false
)

// Model surat untuk UI
data class SuratSaya(
    val id: String,
    val judul: String,
    val deskripsi: String,
    val tanggal: String,
    val status: StatusSurat,
    val nik: String,
    val nama: String,
    val diprosesOlehId: String,
    val disahkanOlehId: String
)

enum class StatusSurat {
    MENUNGGU,
    DIPROSES,
    SELESAI,
    DIBATALKAN
}
