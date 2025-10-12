package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatsuratnikah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMDuplikatSuratNikahRequest

class SPMDuplikatSuratNikahStateManager {
    // Form Data States - Step 1
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
    var kewarganegaraanValue by mutableStateOf("")
        private set
    var noKkValue by mutableStateOf("")
        private set
    var pekerjaanValue by mutableStateOf("")
        private set
    var pendidikanIdValue by mutableStateOf("")
        private set
    var agamaIdValue by mutableStateOf("")
        private set

    // Form Data States - Step 2
    var namaPasanganValue by mutableStateOf("")
        private set
    var tanggalNikahValue by mutableStateOf("")
        private set
    var kepalaKeluargaValue by mutableStateOf("")
        private set
    var kecamatanKuaValue by mutableStateOf("")
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

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updateJenisKelamin(value: String) { jenisKelaminValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateKewarganegaraan(value: String) { kewarganegaraanValue = value }
    fun updateNoKk(value: String) { noKkValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updatePendidikanId(value: String) { pendidikanIdValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }

    // Step 2 Update Functions
    fun updateNamaPasangan(value: String) { namaPasanganValue = value }
    fun updateTanggalNikah(value: String) { tanggalNikahValue = value }
    fun updateKepalaKeluarga(value: String) { kepalaKeluargaValue = value }
    fun updateKecamatanKua(value: String) { kecamatanKuaValue = value }

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

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun clearError() { errorMessage = null }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
        alamatValue = userData.alamat ?: ""
        jenisKelaminValue = userData.jenis_kelamin ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        tempatLahirValue = userData.tempat_lahir ?: ""
        kewarganegaraanValue = userData.kewarganegaraan ?: ""
        noKkValue = userData.no_kk ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
        pendidikanIdValue = userData.pendidikan_id ?: ""
        agamaIdValue = userData.agama_id ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        jenisKelaminValue = ""
        tanggalLahirValue = ""
        tempatLahirValue = ""
        kewarganegaraanValue = ""
        noKkValue = ""
        pekerjaanValue = ""
        pendidikanIdValue = ""
        agamaIdValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        namaPasanganValue = ""
        tanggalNikahValue = ""
        kepalaKeluargaValue = ""
        kecamatanKuaValue = ""
        keperluanValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || jenisKelaminValue.isNotBlank() ||
                tanggalLahirValue.isNotBlank() || tempatLahirValue.isNotBlank() ||
                kewarganegaraanValue.isNotBlank() || noKkValue.isNotBlank() ||
                pekerjaanValue.isNotBlank() || pendidikanIdValue.isNotBlank() ||
                agamaIdValue.isNotBlank() || namaPasanganValue.isNotBlank() ||
                tanggalNikahValue.isNotBlank() || kepalaKeluargaValue.isNotBlank() ||
                kecamatanKuaValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SPMDuplikatSuratNikahRequest {
        return SPMDuplikatSuratNikahRequest(
            agama_id = agamaIdValue,
            alamat = alamatValue,
            jenis_kelamin = jenisKelaminValue,
            kecamatan_kua = kecamatanKuaValue,
            kepala_keluarga = kepalaKeluargaValue,
            keperluan = keperluanValue,
            kewarganegaraan = kewarganegaraanValue,
            nama = namaValue,
            nama_pasangan = namaPasanganValue,
            nik = nikValue,
            no_kk = noKkValue,
            pekerjaan = pekerjaanValue,
            pendidikan_id = pendidikanIdValue,
            tanggal_lahir = tanggalLahirValue,
            tanggal_nikah = tanggalNikahValue,
            tempat_lahir = tempatLahirValue
        )
    }
}