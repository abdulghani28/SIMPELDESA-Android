package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jamkesos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKJamkesosRequest

class SKJamkesosStateManager {
    // Form Data States - Step 1 (Personal Data)
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
    var alamatValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var statusKawinIdValue by mutableStateOf("")
        private set
    var pendidikanIdValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Card Info)
    var noKartuValue by mutableStateOf("")
        private set
    var berlakuDariValue by mutableStateOf("")
        private set
    var berlakuSampaiValue by mutableStateOf("")
        private set

    // Form Data States - Step 3 (Purpose)
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
    var isLoadingStatusKawin by mutableStateOf(false)
        private set
    var isLoadingPendidikan by mutableStateOf(false)
        private set

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set
    var statusKawinErrorMessage by mutableStateOf<String?>(null)
        private set
    var pendidikanErrorMessage by mutableStateOf<String?>(null)
        private set

    // Agama Data
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set
    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
        private set
    var pendidikanList by mutableStateOf<List<PendidikanResponse.Data>>(emptyList())
        private set

    // Step 1 Update Functions (Personal Data)
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updateStatusKawinId(value: String) { statusKawinIdValue = value }
    fun updatePendidikanId(value: String) { pendidikanIdValue = value }

    // Step 2 Update Functions (Card Info)
    fun updateNoKartu(value: String) { noKartuValue = value }
    fun updateBerlakuDari(value: String) { berlakuDariValue = value }
    fun updateBerlakuSampai(value: String) { berlakuSampaiValue = value }

    // Step 3 Update Functions (Purpose)
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
    fun updateStatusKawinLoading(loading: Boolean) { isLoadingStatusKawin = loading }
    fun updatePendidikanLoading(loading: Boolean) { isLoadingPendidikan = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun clearError() { errorMessage = null }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        jenisKelaminValue = userData.jenis_kelamin
        alamatValue = userData.alamat
        pekerjaanValue = userData.pekerjaan
        agamaIdValue = userData.agama_id.toString()
        statusKawinIdValue = userData.status_kawin_id.toString()
        pendidikanIdValue = userData.pendidikan_id.toString()
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        jenisKelaminValue = ""
        alamatValue = ""
        pekerjaanValue = ""
        agamaIdValue = ""
        statusKawinIdValue = ""
        pendidikanIdValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Clear step 2 data
        noKartuValue = ""
        berlakuDariValue = ""
        berlakuSampaiValue = ""

        // Clear step 3 data
        keperluanValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                jenisKelaminValue.isNotBlank() || alamatValue.isNotBlank() ||
                pekerjaanValue.isNotBlank() || agamaIdValue.isNotBlank() ||
                statusKawinIdValue.isNotBlank() || pendidikanIdValue.isNotBlank() ||
                noKartuValue.isNotBlank() || berlakuDariValue.isNotBlank() ||
                berlakuSampaiValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SKJamkesosRequest {
        return SKJamkesosRequest(
            agama_id = agamaIdValue,
            alamat = alamatValue,
            berlaku_dari = berlakuDariValue,
            berlaku_sampai = berlakuSampaiValue,
            jenis_kelamin = jenisKelaminValue,
            keperluan = keperluanValue,
            nama = namaValue,
            nik = nikValue,
            no_kartu = noKartuValue,
            pekerjaan = pekerjaanValue,
            pendidikan_id = pendidikanIdValue,
            status_kawin_id = statusKawinIdValue,
            tanggal_lahir = tanggalLahirValue,
            tempat_lahir = tempatLahirValue
        )
    }
}