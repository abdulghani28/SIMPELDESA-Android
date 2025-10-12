package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMAktaLahirRequest

class SPMAktaLahirStateManager {
    // Form Data States - Step 1 (Pelapor)
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Anak)
    var namaAnakValue by mutableStateOf("")
        private set
    var tanggalLahirAnakValue by mutableStateOf("")
        private set
    var tempatLahirAnakValue by mutableStateOf("")
        private set
    var alamatAnakValue by mutableStateOf("")
        private set

    // Form Data States - Step 3 (Orang Tua)
    var namaAyahValue by mutableStateOf("")
        private set
    var nikAyahValue by mutableStateOf("")
        private set
    var namaIbuValue by mutableStateOf("")
        private set
    var nikIbuValue by mutableStateOf("")
        private set
    var alamatOrangTuaValue by mutableStateOf("")
        private set

    // Form Data States - Step 4 (Keperluan)
    var keperluanValue by mutableStateOf("")
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

    // Step 1 Update Functions (Pelapor)
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }

    // Step 2 Update Functions (Anak)
    fun updateNamaAnak(value: String) { namaAnakValue = value }
    fun updateTanggalLahirAnak(value: String) { tanggalLahirAnakValue = value }
    fun updateTempatLahirAnak(value: String) { tempatLahirAnakValue = value }
    fun updateAlamatAnak(value: String) { alamatAnakValue = value }

    // Step 3 Update Functions (Orang Tua)
    fun updateNamaAyah(value: String) { namaAyahValue = value }
    fun updateNikAyah(value: String) { nikAyahValue = value }
    fun updateNamaIbu(value: String) { namaIbuValue = value }
    fun updateNikIbu(value: String) { nikIbuValue = value }
    fun updateAlamatOrangTua(value: String) { alamatOrangTuaValue = value }

    // Step 4 Update Functions (Keperluan)
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 4) currentStep++ }
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
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
        alamatValue = userData.alamat ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        tempatLahirValue = userData.tempat_lahir ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        pekerjaanValue = ""
        tanggalLahirValue = ""
        tempatLahirValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Clear step 2 data (Anak)
        namaAnakValue = ""
        tanggalLahirAnakValue = ""
        tempatLahirAnakValue = ""
        alamatAnakValue = ""

        // Clear step 3 data (Orang Tua)
        namaAyahValue = ""
        nikAyahValue = ""
        namaIbuValue = ""
        nikIbuValue = ""
        alamatOrangTuaValue = ""

        // Clear step 4 data (Keperluan)
        keperluanValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                tanggalLahirValue.isNotBlank() || tempatLahirValue.isNotBlank() ||
                namaAnakValue.isNotBlank() || tanggalLahirAnakValue.isNotBlank() ||
                tempatLahirAnakValue.isNotBlank() || alamatAnakValue.isNotBlank() ||
                namaAyahValue.isNotBlank() || nikAyahValue.isNotBlank() ||
                namaIbuValue.isNotBlank() || nikIbuValue.isNotBlank() ||
                alamatOrangTuaValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SPMAktaLahirRequest {
        return SPMAktaLahirRequest(
            alamat = alamatValue,
            alamat_anak = alamatAnakValue,
            alamat_orang_tua = alamatOrangTuaValue,
            keperluan = keperluanValue,
            nama = namaValue,
            nama_anak = namaAnakValue,
            nama_ayah = namaAyahValue,
            nama_ibu = namaIbuValue,
            nik = nikValue,
            nik_ayah = nikAyahValue,
            nik_ibu = nikIbuValue,
            pekerjaan = pekerjaanValue,
            tanggal_lahir = tanggalLahirValue,
            tanggal_lahir_anak = tanggalLahirAnakValue,
            tempat_lahir = tempatLahirValue,
            tempat_lahir_anak = tempatLahirAnakValue
        )
    }
}
