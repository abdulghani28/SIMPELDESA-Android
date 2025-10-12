package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.duplikatkelahiran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMDuplikatKelahiranRequest

class SPMDuplikatKelahiranStateManager {
    // Form Data States - Step 1: Informasi Pelapor
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

    // Form Data States - Step 2: Informasi Anak
    var nikAnakValue by mutableStateOf("")
        private set
    var namaAnakValue by mutableStateOf("")
        private set
    var tanggalLahirAnakValue by mutableStateOf("")
        private set
    var tempatLahirAnakValue by mutableStateOf("")
        private set
    var jenisKelaminAnakValue by mutableStateOf("")
        private set
    var agamaIdAnakValue by mutableStateOf("")
        private set
    var alamatAnakValue by mutableStateOf("")
        private set

    // Form Data States - Step 3: Informasi Orang Tua
    var namaAyahValue by mutableStateOf("")
        private set
    var nikAyahValue by mutableStateOf("")
        private set
    var alamatAyahValue by mutableStateOf("")
        private set
    var pekerjaanAyahValue by mutableStateOf("")
        private set
    var namaIbuValue by mutableStateOf("")
        private set
    var nikIbuValue by mutableStateOf("")
        private set
    var alamatIbuValue by mutableStateOf("")
        private set
    var pekerjaanIbuValue by mutableStateOf("")
        private set

    // Form Data States - Step 4: Informasi Pelengkap
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

    // Step 1 Update Functions - Informasi Pelapor
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateAlamat(value: String) { alamatValue = value }
    fun updatePekerjaan(value: String) { pekerjaanValue = value }
    fun updateTanggalLahir(value: String) { tanggalLahirValue = value }
    fun updateTempatLahir(value: String) { tempatLahirValue = value }

    // Step 2 Update Functions - Informasi Anak
    fun updateNikAnak(value: String) { nikAnakValue = value }
    fun updateNamaAnak(value: String) { namaAnakValue = value }
    fun updateTanggalLahirAnak(value: String) { tanggalLahirAnakValue = value }
    fun updateTempatLahirAnak(value: String) { tempatLahirAnakValue = value }
    fun updateJenisKelaminAnak(value: String) { jenisKelaminAnakValue = value }
    fun updateAgamaIdAnak(value: String) { agamaIdAnakValue = value }
    fun updateAlamatAnak(value: String) { alamatAnakValue = value }

    // Step 3 Update Functions - Informasi Orang Tua
    fun updateNamaAyah(value: String) { namaAyahValue = value }
    fun updateNikAyah(value: String) { nikAyahValue = value }
    fun updateAlamatAyah(value: String) { alamatAyahValue = value }
    fun updatePekerjaanAyah(value: String) { pekerjaanAyahValue = value }
    fun updateNamaIbu(value: String) { namaIbuValue = value }
    fun updateNikIbu(value: String) { nikIbuValue = value }
    fun updateAlamatIbu(value: String) { alamatIbuValue = value }
    fun updatePekerjaanIbu(value: String) { pekerjaanIbuValue = value }

    // Step 4 Update Functions - Informasi Pelengkap
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

        // Clear Step 2 data
        nikAnakValue = ""
        namaAnakValue = ""
        tanggalLahirAnakValue = ""
        tempatLahirAnakValue = ""
        jenisKelaminAnakValue = ""
        agamaIdAnakValue = ""
        alamatAnakValue = ""

        // Clear Step 3 data
        namaAyahValue = ""
        nikAyahValue = ""
        alamatAyahValue = ""
        pekerjaanAyahValue = ""
        namaIbuValue = ""
        nikIbuValue = ""
        alamatIbuValue = ""
        pekerjaanIbuValue = ""

        // Clear Step 4 data
        keperluanValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                alamatValue.isNotBlank() || pekerjaanValue.isNotBlank() ||
                tanggalLahirValue.isNotBlank() || tempatLahirValue.isNotBlank() ||
                nikAnakValue.isNotBlank() || namaAnakValue.isNotBlank() ||
                tanggalLahirAnakValue.isNotBlank() || tempatLahirAnakValue.isNotBlank() ||
                jenisKelaminAnakValue.isNotBlank() || agamaIdAnakValue.isNotBlank() ||
                alamatAnakValue.isNotBlank() || namaAyahValue.isNotBlank() ||
                nikAyahValue.isNotBlank() || alamatAyahValue.isNotBlank() ||
                pekerjaanAyahValue.isNotBlank() || namaIbuValue.isNotBlank() ||
                nikIbuValue.isNotBlank() || alamatIbuValue.isNotBlank() ||
                pekerjaanIbuValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SPMDuplikatKelahiranRequest {
        return SPMDuplikatKelahiranRequest(
            nik = nikValue,
            nama = namaValue,
            alamat = alamatValue,
            pekerjaan = pekerjaanValue,
            tanggal_lahir = tanggalLahirValue,
            tempat_lahir = tempatLahirValue,
            nik_anak = nikAnakValue,
            nama_anak = namaAnakValue,
            tanggal_lahir_anak = tanggalLahirAnakValue,
            tempat_lahir_anak = tempatLahirAnakValue,
            jenis_kelamin_anak = jenisKelaminAnakValue,
            agama_id_anak = agamaIdAnakValue,
            alamat_anak = alamatAnakValue,
            nama_ayah = namaAyahValue,
            nik_ayah = nikAyahValue,
            alamat_ayah = alamatAyahValue,
            pekerjaan_ayah = pekerjaanAyahValue,
            nama_ibu = namaIbuValue,
            nik_ibu = nikIbuValue,
            alamat_ibu = alamatIbuValue,
            pekerjaan_ibu = pekerjaanIbuValue,
            keperluan = keperluanValue
        )
    }
}