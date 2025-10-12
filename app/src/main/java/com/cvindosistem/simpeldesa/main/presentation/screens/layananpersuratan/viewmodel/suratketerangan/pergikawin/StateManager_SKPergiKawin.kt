package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pergikawin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPergiKawinRequest

class SKPergiKawinStateManager {
    // Form Data States - Step 1
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var alamatValue by mutableStateOf("")
        private set
    var jenisKelaminValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var statusKawinIdValue by mutableStateOf("")
        private set
    var pendidikanIdValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var kewarganegaraanValue by mutableStateOf("")
        private set

    // Form Data States - Step 2
    var keperluanValue by mutableStateOf("")
        private set
    var tujuanValue by mutableStateOf("")
        private set
    var berlakuMulaiValue by mutableStateOf("")
        private set
    var berlakuSampaiValue by mutableStateOf("")
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
    var isLoadingPendidikan by mutableStateOf(false)
        private set
    var isLoadingStatusKawin by mutableStateOf(false)
        private set

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set
    var pendidikanErrorMessage by mutableStateOf<String?>(null)
        private set
    var statusKawinErrorMessage by mutableStateOf<String?>(null)
        private set

    // Reference Data
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set
    var pendidikanList by mutableStateOf<List<PendidikanResponse.Data>>(emptyList())
        private set
    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
        private set

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updateStatusKawinId(value: String) { statusKawinIdValue = value }
    fun updatePendidikanId(value: String) { pendidikanIdValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateKewarganegaraan(value: String) { kewarganegaraanValue = value }

    // Step 2 Update Functions
    fun updateKeperluan(value: String) { keperluanValue = value }
    fun updateTujuan(value: String) { tujuanValue = value }
    fun updateBerlakuMulai(value: String) { berlakuMulaiValue = value }
    fun updateBerlakuSampai(value: String) { berlakuSampaiValue = value }

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
    fun updatePendidikanLoading(loading: Boolean) { isLoadingPendidikan = loading }
    fun updateStatusKawinLoading(loading: Boolean) { isLoadingStatusKawin = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun updatePendidikanErrorMessage(message: String?) { pendidikanErrorMessage = message }
    fun updateStatusKawinErrorMessage(message: String?) { statusKawinErrorMessage = message }
    fun clearError() { errorMessage = null }

    // Data Setter
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }
    fun updatePendidikanList(list: List<PendidikanResponse.Data>) { pendidikanList = list }
    fun updateStatusKawinList(list: List<StatusKawinResponse.Data>) { statusKawinList = list }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
        tempatLahirValue = userData.tempat_lahir ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        alamatValue = userData.alamat ?: ""
        jenisKelaminValue = userData.jenis_kelamin ?: ""
        agamaIdValue = userData.agama_id ?: ""
        statusKawinIdValue = userData.status_kawin_id ?: ""
        pendidikanIdValue = userData.pendidikan_id ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
        kewarganegaraanValue = userData.kewarganegaraan ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        alamatValue = ""
        jenisKelaminValue = ""
        agamaIdValue = ""
        statusKawinIdValue = ""
        pendidikanIdValue = ""
        pekerjaanValue = ""
        kewarganegaraanValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        keperluanValue = ""
        tujuanValue = ""
        berlakuMulaiValue = ""
        berlakuSampaiValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                alamatValue.isNotBlank() || jenisKelaminValue.isNotBlank() ||
                agamaIdValue.isNotBlank() || statusKawinIdValue.isNotBlank() ||
                pendidikanIdValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                kewarganegaraanValue.isNotBlank() || keperluanValue.isNotBlank() ||
                tujuanValue.isNotBlank() || berlakuMulaiValue.isNotBlank() ||
                berlakuSampaiValue.isNotBlank()
    }

    fun toRequest(): SKPergiKawinRequest {
        return SKPergiKawinRequest(
            nik = nikValue,
            nama = namaValue,
            tempat_lahir = tempatLahirValue,
            tanggal_lahir = tanggalLahirValue,
            alamat = alamatValue,
            jenis_kelamin = jenisKelaminValue,
            agama_id = agamaIdValue,
            status_kawin_id = statusKawinIdValue,
            pendidikan_id = pendidikanIdValue,
            pekerjaan = pekerjaanValue,
            kewarganegaraan = kewarganegaraanValue,
            keperluan = keperluanValue,
            tujuan = tujuanValue,
            berlaku_mulai = berlakuMulaiValue,
            berlaku_sampai = berlakuSampaiValue
        )
    }
}