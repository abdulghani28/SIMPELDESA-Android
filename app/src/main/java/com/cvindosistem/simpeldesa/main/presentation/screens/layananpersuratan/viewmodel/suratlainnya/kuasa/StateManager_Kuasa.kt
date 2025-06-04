package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.kuasa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratlainnya.SuratKuasaRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SuratKuasaStateManager {
    // Form state
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var jabatanValue by mutableStateOf("")
        private set
    var kuasaSebagaiValue by mutableStateOf("")
        private set
    var kuasaUntukValue by mutableStateOf("")
        private set

    // Step 2 fields
    var nikPenerimaValue by mutableStateOf("")
        private set
    var namaPenerimaValue by mutableStateOf("")
        private set
    var jabatanPenerima by mutableStateOf("")
        private set

    // UI state
    var currentStep by mutableIntStateOf(1)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // UI State flow
    private val _uiState = MutableStateFlow(SuratKuasaUiState())
    val uiState = _uiState.asStateFlow()

    // Step 1 field updates
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateJabatan(value: String) { jabatanValue = value }
    fun updateKuasaSebagai(value: String) { kuasaSebagaiValue = value }
    fun updateKuasaUntuk(value: String) { kuasaUntukValue = value }

    // Step 2 field updates
    fun updateNikPenerima(value: String) { nikPenerimaValue = value }
    fun updateNamaPenerima(value: String) { namaPenerimaValue = value }
    fun updateJabatanPenerima(value: String) { jabatanPenerima = value }

    // Navigation
    fun nextStep() { if (currentStep < 2) currentStep++ }
    fun previousStep() { if (currentStep > 1) currentStep-- }

    // Loading states
    fun updateLoading(loading: Boolean) { isLoading = loading }
    fun setUserDataLoading(loading: Boolean) { isLoadingUserData = loading }
    fun setError(message: String?) { errorMessage = message }

    // Dialog states
    fun showConfirmation() { showConfirmationDialog = true }
    fun dismissConfirmation() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Use My Data
    fun setUseMyData(checked: Boolean) { useMyDataChecked = checked }

    fun populateUserData(nik: String, nama: String) {
        nikValue = nik
        namaValue = nama
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        nikValue = ""
        namaValue = ""
        jabatanValue = ""
        kuasaUntukValue = ""
        kuasaSebagaiValue = ""
        nikPenerimaValue = ""
        namaPenerimaValue = ""
        jabatanPenerima = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank()
    }

    fun createRequest(): SuratKuasaRequest {
        return SuratKuasaRequest(
            disahkan_oleh = "",
            disposisi_kuasa_sebagai = kuasaSebagaiValue,
            disposisi_kuasa_untuk = kuasaUntukValue,
            jabatan_pemberi = jabatanValue,
            jabatan_penerima = jabatanPenerima,
            nama_pemberi = namaValue,
            nama_penerima = namaPenerimaValue,
            nik_pemberi = nikValue,
            nik_penerima = nikPenerimaValue,
        )
    }
}