package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.belummemilikiaktalahir

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMBelumMemilikiAktaLahirRequest

class SPMBelumMemilikiAktaLahirStateManager {
    // Form Data States - Step 1 (Informasi Pelapor)
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Informasi Orang Tua)
    var namaAyahValue by mutableStateOf("")
        private set
    var nikAyahValue by mutableStateOf("")
        private set
    var namaIbuValue by mutableStateOf("")
        private set
    var nikIbuValue by mutableStateOf("")
        private set

    // UI States
    var currentStep by mutableIntStateOf(1)
        private set
    var useMyDataChecked by mutableStateOf(false)
        private set
    var showConfirmationDialog by mutableStateOf(false)
        private set
    var showPreviewDialog by mutableStateOf(false)
        private set

    // Loading States
    var isLoading by mutableStateOf(false)
        private set
    var isLoadingUserData by mutableStateOf(false)
        private set

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Step 1 Update Functions (Informasi Pelapor)
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }

    // Step 2 Update Functions (Informasi Orang Tua)
    fun updateNamaAyah(value: String) { namaAyahValue = value }
    fun updateNikAyah(value: String) { nikAyahValue = value }
    fun updateNamaIbu(value: String) { namaIbuValue = value }
    fun updateNikIbu(value: String) { nikIbuValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 2) currentStep++ }
    fun previousStep() { if (currentStep > 1) currentStep-- }

    // Dialog Functions
    fun showConfirmation() { showConfirmationDialog = true }
    fun hideConfirmation() { showConfirmationDialog = false }
    fun showPreview() { showPreviewDialog = true }
    fun hidePreview() { showPreviewDialog = false }

    // Use My Data Function
    fun updateUseMyDataChecked(checked: Boolean) { useMyDataChecked = checked }

    // Loading State Functions
    fun updateLoadingState(loading: Boolean) { isLoading = loading }
    fun updateUserDataLoading(loading: Boolean) { isLoadingUserData = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun clearError() { errorMessage = null }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        alamatValue = userData.alamat
        jenisKelaminValue = userData.jenis_kelamin
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        tempatLahirValue = userData.tempat_lahir
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        jenisKelaminValue = ""
        tanggalLahirValue = ""
        tempatLahirValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        namaAyahValue = ""
        nikAyahValue = ""
        namaIbuValue = ""
        nikIbuValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || jenisKelaminValue.isNotBlank() ||
                tanggalLahirValue.isNotBlank() || tempatLahirValue.isNotBlank() ||
                namaAyahValue.isNotBlank() || nikAyahValue.isNotBlank() ||
                namaIbuValue.isNotBlank() || nikIbuValue.isNotBlank()
    }

    fun toRequest(): SPMBelumMemilikiAktaLahirRequest {
        return SPMBelumMemilikiAktaLahirRequest(
            alamat = alamatValue,
            jenis_kelamin = jenisKelaminValue,
            nama = namaValue,
            nama_ayah = namaAyahValue,
            nama_ibu = namaIbuValue,
            nik = nikValue,
            nik_ayah = nikAyahValue,
            nik_ibu = nikIbuValue,
            tanggal_lahir = tanggalLahirValue,
            tempat_lahir = tempatLahirValue
        )
    }
}