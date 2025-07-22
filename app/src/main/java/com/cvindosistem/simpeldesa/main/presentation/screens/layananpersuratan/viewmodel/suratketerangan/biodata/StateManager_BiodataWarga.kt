package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.PendidikanResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.StatusKawinResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKBiodataWargaRequest

class SKBiodataWargaStateManager {
    // Form Data States - Step 1: Data Diri Pemohon
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var tempatLahirValue by mutableStateOf("")
        private set
    var tanggalLahirValue by mutableStateOf("")
        private set
    var golonganDarahValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var pendidikanIdValue by mutableStateOf("")
        private set
    var disabilitasIdValue by mutableStateOf("")
        private set

    // Form Data States - Step 2: Alamat & Status
    var alamatValue by mutableStateOf("")
        private set
    var statusValue by mutableStateOf("")
        private set
    var hubunganValue by mutableStateOf("")
        private set

    // Form Data States - Step 3: Informasi Perkawinan
    var aktaKawinValue by mutableStateOf("")
        private set
    var tanggalKawinValue by mutableStateOf("")
        private set
    var aktaCeraiValue by mutableStateOf("")
        private set
    var tanggalCeraiValue by mutableStateOf("")
        private set

    // Form Data States - Step 4: Data Orang Tua
    var namaAyahValue by mutableStateOf("")
        private set
    var nikAyahValue by mutableStateOf("")
        private set
    var namaIbuValue by mutableStateOf("")
        private set
    var nikIbuValue by mutableStateOf("")
        private set
    var aktaLahirValue by mutableStateOf("")
        private set

    // Form Data States - Step 5: Keperluan
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
    var isLoadingPendidikan by mutableStateOf(false)
        private set
    var isLoadingDisabilitas by mutableStateOf(false)
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
    var disabilitasErrorMessage by mutableStateOf<String?>(null)
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
//    var disabilitasList by mutableStateOf<List<DisabilitasResponse.Data>>(emptyList())
//        private set

    // Step 1 Update Functions - Data Diri Pemohon
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateGolonganDarah(value: String) { golonganDarahValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updatePendidikanId(value: String) { pendidikanIdValue = value }
    fun updateDisabilitasId(value: String) { disabilitasIdValue = value }

    // Step 2 Update Functions - Alamat & Status
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateStatus(value: String) { statusValue = value }
    fun updateHubungan(value: String) { hubunganValue = value }

    // Step 3 Update Functions - Informasi Perkawinan
    fun updateAktaKawin(value: String) { aktaKawinValue = value }
    fun updateTanggalKawin(value: String) { tanggalKawinValue = value }
    fun updateAktaCerai(value: String) { aktaCeraiValue = value }
    fun updateTanggalCerai(value: String) { tanggalCeraiValue = value }

    // Step 4 Update Functions - Data Orang Tua
    fun updateNamaAyah(value: String) { namaAyahValue = value }
    fun updateNikAyah(value: String) { nikAyahValue = value }
    fun updateNamaIbu(value: String) { namaIbuValue = value }
    fun updateNikIbu(value: String) { nikIbuValue = value }
    fun updateAktaLahir(value: String) { aktaLahirValue = value }

    // Step 5 Update Functions - Keperluan
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Navigation Functions
    fun updateCurrentStep(step: Int) { currentStep = step }
    fun nextStep() { if (currentStep < 5) currentStep++ }
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
    fun updateDisabilitasLoading(loading: Boolean) { isLoadingDisabilitas = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun updatePendidikanErrorMessage(message: String?) { pendidikanErrorMessage = message }
    fun updateStatusKawinErrorMessage(message: String?) { statusKawinErrorMessage = message }
    fun updateDisabilitasErrorMessage(message: String?) { disabilitasErrorMessage = message }
    fun clearError() { errorMessage = null }

    // Data Setter
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }
    fun updatePendidikanList(list: List<PendidikanResponse.Data>) { pendidikanList = list }
    fun updateStatusKawinList(list: List<StatusKawinResponse.Data>) { statusKawinList = list }
//    fun updateDisabilitasList(list: List<DisabilitasResponse.Data>) { disabilitasList = list }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik
        namaValue = userData.nama_warga
        tempatLahirValue = userData.tempat_lahir
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir)
        agamaIdValue = userData.agama_id
        pendidikanIdValue = userData.pendidikan_id
        pekerjaanValue = userData.pekerjaan
        alamatValue = userData.alamat
        // Note: Some fields might not be available in UserInfoResponse
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        tempatLahirValue = ""
        tanggalLahirValue = ""
        golonganDarahValue = ""
        agamaIdValue = ""
        pekerjaanValue = ""
        pendidikanIdValue = ""
        disabilitasIdValue = ""
        alamatValue = ""
        // Don't clear other fields as they're not from user data
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Clear all other form data
        statusValue = ""
        hubunganValue = ""
        aktaKawinValue = ""
        tanggalKawinValue = ""
        aktaCeraiValue = ""
        tanggalCeraiValue = ""
        namaAyahValue = ""
        nikAyahValue = ""
        namaIbuValue = ""
        nikIbuValue = ""
        aktaLahirValue = ""
        keperluanValue = ""

        // Clear UI states
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                tempatLahirValue.isNotBlank() || tanggalLahirValue.isNotBlank() ||
                golonganDarahValue.isNotBlank() ||
                agamaIdValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                pendidikanIdValue.isNotBlank() || disabilitasIdValue.isNotBlank() ||
                alamatValue.isNotBlank() || statusValue.isNotBlank() ||
                hubunganValue.isNotBlank() || aktaKawinValue.isNotBlank() ||
                tanggalKawinValue.isNotBlank() || aktaCeraiValue.isNotBlank() ||
                tanggalCeraiValue.isNotBlank() || namaAyahValue.isNotBlank() ||
                nikAyahValue.isNotBlank() || namaIbuValue.isNotBlank() ||
                nikIbuValue.isNotBlank() || aktaLahirValue.isNotBlank() ||
                keperluanValue.isNotBlank()
    }

    fun toRequest(): SKBiodataWargaRequest {
        return SKBiodataWargaRequest(
            nik = nikValue,
            nama = namaValue,
            tempat_lahir = tempatLahirValue,
            tanggal_lahir = tanggalLahirValue,
            golongan_darah = golonganDarahValue,
            agama_id = agamaIdValue,
            pekerjaan = pekerjaanValue,
            pendidikan_id = pendidikanIdValue,
            disabilitas_id = if (disabilitasIdValue.isBlank()) null else disabilitasIdValue,
            alamat = alamatValue,
            status = statusValue,
            hubungan = hubunganValue,
            akta_kawin = aktaKawinValue,
            tanggal_kawin = tanggalKawinValue,
            akta_cerai = aktaCeraiValue,
            tanggal_cerai = tanggalCeraiValue,
            nama_ayah = namaAyahValue,
            nik_ayah = nikAyahValue,
            nama_ibu = namaIbuValue,
            nik_ibu = nikIbuValue,
            akta_lahir = aktaLahirValue,
            keperluan = keperluanValue
        )
    }
}