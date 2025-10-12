package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.perubahankk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMPerubahanKKRequest

class SPMPerubahanKKStateManager {
    // Form Data States - Step 1
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var kewarganegaraanValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set

    // Form Data States - Step 2
    var alamatValue by mutableStateOf("")
        private set
    var noKkValue by mutableStateOf("")
        private set
    var alasanPermohonanValue by mutableStateOf("")
        private set

    // Form Data States - Step 3
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

    // Reference Data
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateKewarganegaraan(value: String) { kewarganegaraanValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }

    // Step 2 Update Functions
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateNoKk(value: String) { noKkValue = value }
    fun updateAlasanPermohonan(value: String) { alasanPermohonanValue = value }

    // Step 3 Update Functions
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 3) currentStep++ }
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
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
        tempatLahirValue = userData.tempat_lahir ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        jenisKelaminValue = userData.jenis_kelamin ?: ""
        kewarganegaraanValue = userData.kewarganegaraan ?: ""
        agamaIdValue = userData.agama_id ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
        alamatValue = userData.alamat ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        kewarganegaraanValue = ""
        agamaIdValue = ""
        pekerjaanValue = ""
        alamatValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        noKkValue = ""
        alasanPermohonanValue = ""
        keperluanValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                jenisKelaminValue.isNotBlank() || kewarganegaraanValue.isNotBlank() ||
                agamaIdValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                alamatValue.isNotBlank() || noKkValue.isNotBlank() ||
                alasanPermohonanValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SPMPerubahanKKRequest {
        return SPMPerubahanKKRequest(
            nik = nikValue,
            nama = namaValue,
            tempat_lahir = tempatLahirValue,
            tanggal_lahir = tanggalLahirValue,
            jenis_kelamin = jenisKelaminValue,
            kewarganegaraan = kewarganegaraanValue,
            agama_id = agamaIdValue,
            pekerjaan = pekerjaanValue,
            alamat = alamatValue,
            no_kk = noKkValue,
            alasan_permohonan = alasanPermohonanValue,
            keperluan = keperluanValue
        )
    }
}