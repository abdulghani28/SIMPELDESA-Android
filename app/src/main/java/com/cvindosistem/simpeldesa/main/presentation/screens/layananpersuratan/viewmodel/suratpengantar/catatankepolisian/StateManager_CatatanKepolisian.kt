package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.catatankepolisian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SPCatatanKepolisianStateManager {
    private val _uiState = MutableStateFlow(SPCatatanKepolisianUiState())
    val uiState = _uiState.asStateFlow()

    // Current step
    var currentStep by mutableIntStateOf(1)
        private set

    // Dialog states
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Step 1 - Informasi Pelapor
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var selectedGender by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set

    // Step 2 - Keperluan
    var keperluanValue by mutableStateOf("")
        private set

    // Update functions for Step 1
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateGender(value: String) { selectedGender = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateAlamat(value: String) { alamatValue = value }

    // Update functions for Step 2
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Dialog management
    fun showConfirmationDialog() { showConfirmationDialog = true }
    fun dismissConfirmationDialog() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Step management
    fun updateCurrentStep(step: Int) { currentStep = step }

    // Form data check
    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    // Reset form
    fun resetForm() {
        currentStep = 1
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
        keperluanValue = ""
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    // Fill user data
    fun fillUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        selectedGender = userData.jenis_kelamin
        pekerjaanValue = userData.pekerjaan
        alamatValue = userData.alamat
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        selectedGender = ""
        pekerjaanValue = ""
        alamatValue = ""
    }
}