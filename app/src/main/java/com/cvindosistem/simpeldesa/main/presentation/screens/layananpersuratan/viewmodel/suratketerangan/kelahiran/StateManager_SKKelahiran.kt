package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kelahiran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SKKelahiranStateManager {
    // Form state properties
    var namaValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var jamLahirValue by mutableStateOf("")
        private set
    var anakKeValue by mutableIntStateOf(1)
        private set

    // Step 2 - Informasi Ayah
    var namaAyahValue by mutableStateOf("")
        private set
    var nikAyahValue by mutableStateOf("")
        private set
    var tempatLahirAyahValue by mutableStateOf("")
        private set
    var tanggalLahirAyahValue by mutableStateOf("")
        private set
    var alamatAyahValue by mutableStateOf("")
        private set

    // Step 3 - Informasi Ibu - Copy pattern dari Step 2
    var namaIbuValue by mutableStateOf("")
        private set
    var nikIbuValue by mutableStateOf("")
        private set
    var tempatLahirIbuValue by mutableStateOf("")
        private set
    var tanggalLahirIbuValue by mutableStateOf("")
        private set
    var alamatIbuValue by mutableStateOf("")
        private set

    // Step 4 - Keperluan
    var keperluanValue by mutableStateOf("")
        private set

    // Navigation state
    var currentStep by mutableIntStateOf(1)
        private set

    // Dialog states
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Loading states
    var isLoading by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set

    // Error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Update functions - Step 1
    // Step 1 - Informasi Anak Update Functions
    fun updateNama(value: String) {
        namaValue = value
    }

    fun updateJenisKelamin(value: String) {
        jenisKelaminValue = value
    }

    fun updateTempatLahir(value: String) {
        tempatLahirValue = value
    }

    fun updateTanggalLahir(value: String) {
        tanggalLahirValue = value
    }

    fun updateJamLahir(value: String) {
        jamLahirValue = value
    }

    fun updateAnakKe(value: Int) {
        anakKeValue = value
    }

    // Step 2 - Informasi Ayah Update Functions
    fun updateNamaAyah(value: String) {
        namaAyahValue = value
    }

    fun updateNikAyah(value: String) {
        nikAyahValue = value
    }

    fun updateTempatLahirAyah(value: String) {
        tempatLahirAyahValue = value
    }

    fun updateTanggalLahirAyah(value: String) {
        tanggalLahirAyahValue = value
    }

    fun updateAlamatAyah(value: String) {
        alamatAyahValue = value
    }

    // Step 3 - Informasi Ibu Update Functions
    fun updateNamaIbu(value: String) {
        namaIbuValue = value
    }

    fun updateNikIbu(value: String) {
        nikIbuValue = value
    }

    fun updateTempatLahirIbu(value: String) {
        tempatLahirIbuValue = value
    }

    fun updateTanggalLahirIbu(value: String) {
        tanggalLahirIbuValue = value
    }

    fun updateAlamatIbu(value: String) {
        alamatIbuValue = value
    }

    // Step 4 - Keperluan Update Function
    fun updateKeperluan(value: String) {
        keperluanValue = value
    }

    // Navigation functions
    fun nextStep() {
        if (currentStep < 4) currentStep += 1
    }

    fun previousStep() {
        if (currentStep > 1) currentStep -= 1
    }

    // Dialog functions
    fun showConfirmation() { showConfirmationDialog = true }
    fun dismissConfirmationDialog() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Loading functions
    fun updateLoading(loading: Boolean) { isLoading = loading }
    fun updateLoadingUserData(loading: Boolean) { isLoadingUserData = loading }
    fun updateUseMyDataChecked(checked: Boolean) { useMyDataChecked = checked }

    // Error functions
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun clearError() { errorMessage = null }

    // Reset function
    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false

        // Reset Step 1
        namaValue = ""
        jenisKelaminValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jamLahirValue = ""
        anakKeValue = 1

        // Reset Step 2 - Copy pattern dari kode asli
        namaAyahValue = ""
        nikAyahValue = ""
        // ... (copy semua reset values dari kode asli)

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    // Utility functions
    fun hasFormData(): Boolean {
        return namaValue.isNotBlank() || jenisKelaminValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                jamLahirValue.isNotBlank() || anakKeValue > 1 ||
                namaAyahValue.isNotBlank() || nikAyahValue.isNotBlank() ||
                // ... (copy semua kondisi dari kode asli)
                keperluanValue.isNotBlank()
    }

    fun clearUserData() {
        namaAyahValue = ""
        alamatAyahValue = ""
    }
}