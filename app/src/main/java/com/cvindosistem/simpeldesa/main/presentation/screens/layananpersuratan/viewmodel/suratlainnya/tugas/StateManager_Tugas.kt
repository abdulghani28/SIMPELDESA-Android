package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.plus

class SuratTugasStateManager {
    // Form States
    private val _uiState = MutableStateFlow(SuratTugasUiState())
    val uiState = _uiState.asStateFlow()

    // Step 1 - Informasi Penerima Tugas
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var jabatanValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Tugas
    var ditugaskanUntukValue by mutableStateOf("")
        private set
    var deskripsiValue by mutableStateOf("")
        private set
    var disahkanOlehValue by mutableStateOf("")
        private set

    // Navigation and UI States
    var currentStep by mutableIntStateOf(1)
        private set
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Additional Recipients
    var additionalRecipients by mutableStateOf<List<AdditionalRecipient>>(emptyList())
        private set

    // Step 1 Update Functions
    fun updateNik(value: String) {
        nikValue = value
    }

    fun updateNama(value: String) {
        namaValue = value
    }

    fun updateJabatan(value: String) {
        jabatanValue = value
    }

    // Step 2 Update Functions
    fun updateDitugaskanUntuk(value: String) {
        ditugaskanUntukValue = value
    }

    fun updateDeskripsi(value: String) {
        deskripsiValue = value
    }

    fun updateDisahkanOleh(value: String) {
        disahkanOlehValue = value
    }

    // Additional Recipients Management
    fun addAdditionalRecipient() {
        additionalRecipients = additionalRecipients + AdditionalRecipient()
    }

    fun removeAdditionalRecipient(index: Int) {
        if (index >= 0 && index < additionalRecipients.size) {
            additionalRecipients = additionalRecipients.toMutableList().apply {
                removeAt(index)
            }
        }
    }

    fun updateAdditionalRecipientNik(index: Int, value: String) {
        if (index >= 0 && index < additionalRecipients.size) {
            additionalRecipients = additionalRecipients.toMutableList().apply {
                this[index] = this[index].copy(nik = value)
            }
        }
    }

    fun updateAdditionalRecipientNama(index: Int, value: String) {
        if (index >= 0 && index < additionalRecipients.size) {
            additionalRecipients = additionalRecipients.toMutableList().apply {
                this[index] = this[index].copy(nama = value)
            }
        }
    }

    fun updateAdditionalRecipientJabatan(index: Int, value: String) {
        if (index >= 0 && index < additionalRecipients.size) {
            additionalRecipients = additionalRecipients.toMutableList().apply {
                this[index] = this[index].copy(jabatan = value)
            }
        }
    }


    // Navigation
    fun updateCurrentStep(step: Int) {
        currentStep = step
    }

    fun showConfirmationDialog() {
        showConfirmationDialog = true
    }

    fun dismissConfirmationDialog() {
        showConfirmationDialog = false
    }

    fun showPreview() {
        showPreviewDialog = true
    }

    fun dismissPreview() {
        showPreviewDialog = false
    }

    // Reset Form
    fun resetForm() {
        currentStep = 1
        nikValue = ""
        namaValue = ""
        jabatanValue = ""
        additionalRecipients = emptyList()
        ditugaskanUntukValue = ""
        deskripsiValue = ""
        disahkanOlehValue = ""
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    // Check if form has data
    fun hasFormData(): Boolean {
        val hasMainData = nikValue.isNotBlank() || namaValue.isNotBlank() ||
                ditugaskanUntukValue.isNotBlank() || deskripsiValue.isNotBlank()

        val hasAdditionalData = additionalRecipients.any { recipient ->
            recipient.nik.isNotBlank() || recipient.nama.isNotBlank() || recipient.jabatan.isNotBlank()
        }

        return hasMainData || hasAdditionalData
    }

    data class AdditionalRecipient(
        val nik: String = "",
        val nama: String = "",
        val jabatan: String = ""
    )
}