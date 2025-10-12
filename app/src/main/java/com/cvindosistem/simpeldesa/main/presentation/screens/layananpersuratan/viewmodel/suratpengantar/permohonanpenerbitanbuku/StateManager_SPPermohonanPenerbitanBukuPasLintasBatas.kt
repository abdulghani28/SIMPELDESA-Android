package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar.SPPermohonanPenerbitanBukuPasLintasBatasRequest

class SPPermohonanPenerbitanBukuPasLintasBatasStateManager {
    // Form Data States - Step 1: Data Pemohon
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
    var statusKawinIdValue by mutableStateOf("")
        private set

    // Form Data States - Step 2: Alamat & Keluarga
    var alamatValue by mutableStateOf("")
        private set
    var noKkValue by mutableStateOf("")
        private set
    var kepalaKeluargaValue by mutableStateOf("")
        private set

    // Form Data States - Step 3: Anggota Keluarga yang Ikut
    var anggotaKeluargaValue by mutableStateOf<List<SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga>>(emptyList())
        private set

    // Form Data States - Step 4: Keperluan
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

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set
    var statusKawinErrorMessage by mutableStateOf<String?>(null)
        private set

    // Reference Data
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set
    var statusKawinList by mutableStateOf<List<StatusKawinResponse.Data>>(emptyList())
        private set

    // Step 1 Update Functions - Data Pemohon
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateKewarganegaraan(value: String) { kewarganegaraanValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateStatusKawinId(value: String) { statusKawinIdValue = value }

    // Step 2 Update Functions - Alamat & Keluarga
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateNoKk(value: String) { noKkValue = value }
    fun updateKepalaKeluarga(value: String) { kepalaKeluargaValue = value }

    // Step 3 Update Functions - Anggota Keluarga yang Ikut
    fun updateAnggotaKeluarga(value: List<SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga>) {
        anggotaKeluargaValue = value
    }

    fun addAnggotaKeluarga(anggota: SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga) {
        anggotaKeluargaValue = anggotaKeluargaValue + anggota
    }

    fun removeAnggotaKeluarga(index: Int) {
        anggotaKeluargaValue = anggotaKeluargaValue.toMutableList().apply { removeAt(index) }
    }

    fun updateAnggotaKeluargaAt(index: Int, anggota: SPPermohonanPenerbitanBukuPasLintasBatasRequest.AnggotaKeluarga) {
        anggotaKeluargaValue = anggotaKeluargaValue.toMutableList().apply { set(index, anggota) }
    }

    // Step 4 Update Functions - Keperluan
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
    fun updateAgamaLoading(loading: Boolean) { isLoadingAgama = loading }
    fun updateStatusKawinLoading(loading: Boolean) { isLoadingStatusKawin = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun updateStatusKawinErrorMessage(message: String?) { statusKawinErrorMessage = message }
    fun clearError() { errorMessage = null }

    // Data Setter
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }
    fun updateStatusKawinList(list: List<StatusKawinResponse.Data>) { statusKawinList = list }

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
        statusKawinIdValue = userData.status_kawin_id ?: ""
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
        statusKawinIdValue = ""
        alamatValue = ""
        noKkValue = ""
        kepalaKeluargaValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        anggotaKeluargaValue = emptyList()
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
                statusKawinIdValue.isNotBlank() || alamatValue.isNotBlank() ||
                noKkValue.isNotBlank() || kepalaKeluargaValue.isNotBlank() ||
                anggotaKeluargaValue.isNotEmpty() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SPPermohonanPenerbitanBukuPasLintasBatasRequest {
        return SPPermohonanPenerbitanBukuPasLintasBatasRequest(
            agama_id = agamaIdValue,
            alamat = alamatValue,
            anggota_keluarga = anggotaKeluargaValue,
            jenis_kelamin = jenisKelaminValue,
            kepala_keluarga = kepalaKeluargaValue,
            keperluan = keperluanValue,
            kewarganegaraan = kewarganegaraanValue,
            nama = namaValue,
            nik = nikValue,
            no_kk = noKkValue,
            pekerjaan = pekerjaanValue,
            status_kawin_id = statusKawinIdValue,
            tanggal_lahir = tanggalLahirValue,
            tempat_lahir = tempatLahirValue
        )
    }
}