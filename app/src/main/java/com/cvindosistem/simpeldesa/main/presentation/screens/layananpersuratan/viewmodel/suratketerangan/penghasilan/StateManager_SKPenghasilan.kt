package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.penghasilan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse

class SKPenghasilanStateManager {
    // Form navigation state
    var currentStep by mutableIntStateOf(1)
        private set

    var useMyDataChecked by mutableStateOf(false)
        private set

    var showConfirmationDialog by mutableStateOf(false)
        private set

    var showPreviewDialog by mutableStateOf(false)
        private set

    // Step 1 - Informasi Pribadi
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set

    // Step 2 - Informasi Orang Tua
    var nikOrtuValue by mutableStateOf("")
        private set
    var namaOrtuValue by mutableStateOf("")
        private set
    var tempatLahirOrtuValue by mutableStateOf("")
        private set
    var tanggalLahirOrtuValue by mutableStateOf("")
        private set
    var jenisKelaminOrtuValue by mutableStateOf("")
        private set
    var alamatOrtuValue by mutableStateOf("")
        private set
    var pekerjaanOrtuValue by mutableStateOf("")
        private set
    var penghasilanOrtuValue by mutableIntStateOf(0)
        private set
    var tanggunganOrtuValue by mutableIntStateOf(0)
        private set

    // Step 3 - Informasi Anak
    var nikAnakValue by mutableStateOf("")
        private set
    var namaAnakValue by mutableStateOf("")
        private set
    var tempatLahirAnakValue by mutableStateOf("")
        private set
    var tanggalLahirAnakValue by mutableStateOf("")
        private set
    var jenisKelaminAnakValue by mutableStateOf("")
        private set
    var namaSekolahAnakValue by mutableStateOf("")
        private set
    var kelasAnakValue by mutableStateOf("")
        private set

    // Navigation functions
    fun nextStep() {
        if (currentStep < 3) currentStep++
    }

    fun previousStep() {
        if (currentStep > 1) currentStep--
    }

    fun updateCurrentStep(step: Int) {
        currentStep = step.coerceIn(1, 3)
    }

    // Dialog functions
    fun showConfirmationDialog() { showConfirmationDialog = true }
    fun dismissConfirmationDialog() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun dismissPreview() { showPreviewDialog = false }

    // Use My Data functions
    fun setUseMyData(checked: Boolean) { useMyDataChecked = checked }

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Step 2 Update Functions
    fun updateNikOrtu(value: String) {
        nikOrtuValue = value
    }

    fun updateNamaOrtu(value: String) {
        namaOrtuValue = value
    }

    fun updateTempatLahirOrtu(value: String) {
        tempatLahirOrtuValue = value
    }

    fun updateTanggalLahirOrtu(value: String) {
        tanggalLahirOrtuValue = value
    }

    fun updateJenisKelaminOrtu(value: String) {
        jenisKelaminOrtuValue = value
    }

    fun updateAlamatOrtu(value: String) {
        alamatOrtuValue = value
    }

    fun updatePekerjaanOrtu(value: String) {
        pekerjaanOrtuValue = value
    }

    fun updatePenghasilanOrtu(value: Int) {
        penghasilanOrtuValue = value
    }

    fun updateTanggunganOrtu(value: Int) {
        tanggunganOrtuValue = value
    }

    // Step 3 Update Functions
    fun updateNikAnak(value: String) {
        nikAnakValue = value
    }

    fun updateNamaAnak(value: String) {
        namaAnakValue = value
    }

    fun updateTempatLahirAnak(value: String) {
        tempatLahirAnakValue = value
    }

    fun updateTanggalLahirAnak(value: String) {
        tanggalLahirAnakValue = value
    }

    fun updateJenisKelaminAnak(value: String) {
        jenisKelaminAnakValue = value
    }

    fun updateNamaSekolahAnak(value: String) {
        namaSekolahAnakValue = value
    }

    fun updateKelasAnak(value: String) {
        kelasAnakValue = value
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || keperluanValue.isNotBlank() ||
                nikOrtuValue.isNotBlank() || namaOrtuValue.isNotBlank() ||
                tempatLahirOrtuValue.isNotBlank() || tanggalLahirOrtuValue.isNotBlank() ||
                jenisKelaminOrtuValue.isNotBlank() || alamatOrtuValue.isNotBlank() ||
                pekerjaanOrtuValue.isNotBlank() || penghasilanOrtuValue > 0 ||
                tanggunganOrtuValue > 0 || nikAnakValue.isNotBlank() ||
                namaAnakValue.isNotBlank() || tempatLahirAnakValue.isNotBlank() ||
                tanggalLahirAnakValue.isNotBlank() || jenisKelaminAnakValue.isNotBlank() ||
                namaSekolahAnakValue.isNotBlank() || kelasAnakValue.isNotBlank()
    }

    // Reset form
    internal fun resetForm() {
        currentStep = 1
        useMyDataChecked = false

        // Step 1 - Informasi Pribadi
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        keperluanValue = ""

        // Step 2 - Informasi Orang Tua
        nikOrtuValue = ""
        namaOrtuValue = ""
        tempatLahirOrtuValue = ""
        tanggalLahirOrtuValue = ""
        jenisKelaminOrtuValue = ""
        alamatOrtuValue = ""
        pekerjaanOrtuValue = ""
        penghasilanOrtuValue = 0
        tanggunganOrtuValue = 0

        // Step 3 - Informasi Anak
        nikAnakValue = ""
        namaAnakValue = ""
        tempatLahirAnakValue = ""
        tanggalLahirAnakValue = ""
        jenisKelaminAnakValue = ""
        namaSekolahAnakValue = ""
        kelasAnakValue = ""

        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun loadUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        alamatValue = userData.alamat
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        alamatValue = ""
    }
}