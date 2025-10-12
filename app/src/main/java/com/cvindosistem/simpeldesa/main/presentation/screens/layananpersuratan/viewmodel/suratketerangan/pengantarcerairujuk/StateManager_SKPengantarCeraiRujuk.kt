package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pengantarcerairujuk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKPengantarCeraiRujukRequest

class SKPengantarCeraiRujukStateManager {
    // Form Data States - Step 1 (Informasi Pemohon)
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
    var agamaIdValue by mutableStateOf("")
        private set
    var kewarganegaraanValue by mutableStateOf("")
        private set
    var namaAyahValue by mutableStateOf("")
        private set
    var nikAyahValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Informasi Pasangan)
    var nikPasanganValue by mutableStateOf("")
        private set
    var namaPasanganValue by mutableStateOf("")
        private set
    var alamatPasanganValue by mutableStateOf("")
        private set
    var pekerjaanPasanganValue by mutableStateOf("")
        private set
    var tanggalLahirPasanganValue by mutableStateOf("")
        private set
    var tempatLahirPasanganValue by mutableStateOf("")
        private set
    var agamaIdPasanganValue by mutableStateOf("")
        private set
    var kewarganegaraanPasanganValue by mutableStateOf("")
        private set
    var namaAyahPasanganValue by mutableStateOf("")
        private set
    var nikAyahPasanganValue by mutableStateOf("")
        private set

    // Form Data States - Step 3 (Informasi Pelengkap)
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
    fun updateAlamat(value: String) { alamatValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }
    fun updateAgamaId(value: String) { agamaIdValue = value }
    fun updateKewarganegaraan(value: String) { kewarganegaraanValue = value }
    fun updateNamaAyah(value: String) { namaAyahValue = value }
    fun updateNikAyah(value: String) { nikAyahValue = value }

    // Step 2 Update Functions
    fun updateNikPasangan(value: String) { nikPasanganValue = value }
    fun updateNamaPasangan(value: String) { namaPasanganValue = value }
    fun updateAlamatPasangan(value: String) { alamatPasanganValue = value }
    fun updatePekerjaanPasangan(value: String) { pekerjaanPasanganValue = value }
    fun updateTanggalLahirPasangan(value: String) { tanggalLahirPasanganValue = value }
    fun updateTempatLahirPasangan(value: String) { tempatLahirPasanganValue = value }
    fun updateAgamaIdPasangan(value: String) { agamaIdPasanganValue = value }
    fun updateKewarganegaraanPasangan(value: String) { kewarganegaraanPasanganValue = value }
    fun updateNamaAyahPasangan(value: String) { namaAyahPasanganValue = value }
    fun updateNikAyahPasangan(value: String) { nikAyahPasanganValue = value }

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
        alamatValue = userData.alamat ?: ""
        pekerjaanValue = userData.pekerjaan ?: ""
        tanggalLahirValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        tempatLahirValue = userData.tempat_lahir ?: ""
        agamaIdValue = userData.agama_id ?: ""
        kewarganegaraanValue = userData.kewarganegaraan ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
        alamatValue = ""
        pekerjaanValue = ""
        tanggalLahirValue = ""
        tempatLahirValue = ""
        agamaIdValue = ""
        kewarganegaraanValue = ""
        namaAyahValue = ""
        nikAyahValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        // Clear Step 2 data
        nikPasanganValue = ""
        namaPasanganValue = ""
        alamatPasanganValue = ""
        pekerjaanPasanganValue = ""
        tanggalLahirPasanganValue = ""
        tempatLahirPasanganValue = ""
        agamaIdPasanganValue = ""
        kewarganegaraanPasanganValue = ""
        namaAyahPasanganValue = ""
        nikAyahPasanganValue = ""
        // Clear Step 3 data
        keperluanValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                tanggalLahirValue.isNotBlank() || tempatLahirValue.isNotBlank() ||
                agamaIdValue.isNotBlank() || kewarganegaraanValue.isNotBlank() ||
                namaAyahValue.isNotBlank() || nikAyahValue.isNotBlank() ||
                nikPasanganValue.isNotBlank() || namaPasanganValue.isNotBlank() ||
                alamatPasanganValue.isNotBlank() || pekerjaanPasanganValue.isNotBlank() ||
                tanggalLahirPasanganValue.isNotBlank() || tempatLahirPasanganValue.isNotBlank() ||
                agamaIdPasanganValue.isNotBlank() || kewarganegaraanPasanganValue.isNotBlank() ||
                namaAyahPasanganValue.isNotBlank() || nikAyahPasanganValue.isNotBlank() ||
                keperluanValue.isNotBlank()
    }

    fun toRequest(): SKPengantarCeraiRujukRequest {
        return SKPengantarCeraiRujukRequest(
            agama_id = agamaIdValue,
            agama_id_pasangan = agamaIdPasanganValue,
            alamat = alamatValue,
            alamat_pasangan = alamatPasanganValue,
            keperluan = keperluanValue,
            kewarganegaraan = kewarganegaraanValue,
            kewarganegaraan_pasangan = kewarganegaraanPasanganValue,
            nama = namaValue,
            nama_ayah = namaAyahValue,
            nama_ayah_pasangan = namaAyahPasanganValue,
            nama_pasangan = namaPasanganValue,
            nik = nikValue,
            nik_ayah = nikAyahValue,
            nik_ayah_pasangan = nikAyahPasanganValue,
            nik_pasangan = nikPasanganValue,
            pekerjaan = pekerjaanValue,
            pekerjaan_pasangan = pekerjaanPasanganValue,
            tanggal_lahir = tanggalLahirValue,
            tanggal_lahir_pasangan = tanggalLahirPasanganValue,
            tempat_lahir = tempatLahirValue,
            tempat_lahir_pasangan = tempatLahirPasanganValue
        )
    }
}