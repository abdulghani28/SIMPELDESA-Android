package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.belummemilikipbb

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBelumMemilikiPBBRequest

class SKBelumMemilikiPBBStateManager {
    // Form Data States - Step 1
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var statusKawinIdValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set

    // Form Data States - Step 2
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
    var isLoadingAgama by mutableStateOf(false)
        private set

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set

    // Agama Data
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateStatusKawinId(value: String) { statusKawinIdValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }

    // Step 2 Update Function
    fun updateKeperluan(value: String) { keperluanValue = value }

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
    fun updateAgamaLoading(loading: Boolean) { isLoadingAgama = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun clearError() { errorMessage = null }

    // Data Setter
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        alamatValue = userData.alamat
        jenisKelaminValue = userData.jenis_kelamin
        agamaIdValue = userData.agama_id
        pekerjaanValue = userData.pekerjaan
        statusKawinIdValue = userData.status_kawin_id
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        tempatLahirValue = userData.tempat_lahir
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        jenisKelaminValue = ""
        agamaIdValue = ""
        pekerjaanValue = ""
        statusKawinIdValue = ""
        tanggalLahirValue = ""
        tempatLahirValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        keperluanValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || jenisKelaminValue.isNotBlank() ||
                agamaIdValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                statusKawinIdValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SKBelumMemilikiPBBRequest {
        return SKBelumMemilikiPBBRequest(
            agama_id = agamaIdValue,
            alamat = alamatValue,
            jenis_kelamin = jenisKelaminValue,
            keperluan = keperluanValue,
            nama = namaValue,
            nik = nikValue,
            pekerjaan = pekerjaanValue,
            status_kawin_id = statusKawinIdValue,
            tanggal_lahir = tanggalLahirValue,
            tempat_lahir = tempatLahirValue
        )
    }
}