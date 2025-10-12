package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.HubunganResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan.SKLahirMatiRequest

class SKLahirMatiStateManager {
    // Form Data States - Step 1
    var nikValue by mutableStateOf("")
        private set
    var namaValue by mutableStateOf("")
        private set
    var keperluanValue by mutableStateOf("")
        private set

    // Form Data States - Step 2
    var nikIbuValue by mutableStateOf("")
        private set
    var namaIbuValue by mutableStateOf("")
        private set
    var tempatLahirIbuValue by mutableStateOf("")
        private set
    var tanggalLahirIbuValue by mutableStateOf("")
        private set
    var agamaIbuIdValue by mutableStateOf("")
        private set
    var kewarganegaraanIbuIdValue by mutableStateOf("")
        private set
    var pekerjaanIbuValue by mutableStateOf("")
        private set
    var alamatIbuValue by mutableStateOf("")
        private set

    // Form Data States - Step 3
    var lamaDikandungValue by mutableStateOf("")
        private set
    var tanggalMeninggalValue by mutableStateOf("")
        private set
    var tempatMeninggalValue by mutableStateOf("")
        private set
    var hubunganIdValue by mutableStateOf("")
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
    var isLoadingHubungan by mutableStateOf(false)
        private set

    // Error States
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var agamaErrorMessage by mutableStateOf<String?>(null)
        private set
    var hubunganErrorMessage by mutableStateOf<String?>(null)
        private set

    // Data Lists
    var agamaList by mutableStateOf<List<AgamaResponse.Data>>(emptyList())
        private set
    var hubunganList by mutableStateOf<List<HubunganResponse.Data>>(emptyList())
        private set

    // Step 1 Update Functions
    fun updateNik(value: String) { nikValue = value }
    fun updateNama(value: String) { namaValue = value }
    fun updateKeperluan(value: String) { keperluanValue = value }

    // Step 2 Update Functions
    fun updateNikIbu(value: String) { nikIbuValue = value }
    fun updateNamaIbu(value: String) { namaIbuValue = value }
    fun updateTempatLahirIbu(value: String) { tempatLahirIbuValue = value }
    fun updateTanggalLahirIbu(value: String) { tanggalLahirIbuValue = value }
    fun updateAgamaIbuId(value: String) { agamaIbuIdValue = value }
    fun updateKewarganegaraanIbuId(value: String) { kewarganegaraanIbuIdValue = value }
    fun updatePekerjaanIbu(value: String) { pekerjaanIbuValue = value }
    fun updateAlamatIbu(value: String) { alamatIbuValue = value }

    // Step 3 Update Functions
    fun updateLamaDikandung(value: String) { lamaDikandungValue = value }
    fun updateTanggalMeninggal(value: String) { tanggalMeninggalValue = value }
    fun updateTempatMeninggal(value: String) { tempatMeninggalValue = value }
    fun updateHubunganId(value: String) { hubunganIdValue = value }

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
    fun updateHubunganLoading(loading: Boolean) { isLoadingHubungan = loading }

    // Error Handling
    fun updateErrorMessage(message: String?) { errorMessage = message }
    fun updateAgamaErrorMessage(message: String?) { agamaErrorMessage = message }
    fun updateHubunganErrorMessage(message: String?) { hubunganErrorMessage = message }
    fun clearError() { errorMessage = null }

    // Data Setter
    fun updateAgamaList(list: List<AgamaResponse.Data>) { agamaList = list }
    fun updateHubunganList(list: List<HubunganResponse.Data>) { hubunganList = list }

    // Populate / Clear User Data
    fun populateUserData(userData: UserInfoResponse.Data) {
        nikValue = userData.nik ?: ""
        namaValue = userData.nama_warga ?: ""
    }

    fun clearUserData() {
        nikValue = ""
        namaValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()
        keperluanValue = ""
        nikIbuValue = ""
        namaIbuValue = ""
        tempatLahirIbuValue = ""
        tanggalLahirIbuValue = ""
        agamaIbuIdValue = ""
        kewarganegaraanIbuIdValue = ""
        pekerjaanIbuValue = ""
        alamatIbuValue = ""
        lamaDikandungValue = ""
        tanggalMeninggalValue = ""
        tempatMeninggalValue = ""
        hubunganIdValue = ""
        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikValue.isNotBlank() || namaValue.isNotBlank() ||
                keperluanValue.isNotBlank() || nikIbuValue.isNotBlank() ||
                namaIbuValue.isNotBlank() || tempatLahirIbuValue.isNotBlank() ||
                tanggalLahirIbuValue.isNotBlank() || agamaIbuIdValue.isNotBlank() ||
                kewarganegaraanIbuIdValue.isNotBlank() || pekerjaanIbuValue.isNotBlank() ||
                alamatIbuValue.isNotBlank() || lamaDikandungValue.isNotBlank() ||
                tanggalMeninggalValue.isNotBlank() || tempatMeninggalValue.isNotBlank() ||
                hubunganIdValue.isNotBlank()
    }

    fun toRequest(): SKLahirMatiRequest {
        return SKLahirMatiRequest(
            nik = nikValue,
            nama = namaValue,
            keperluan = keperluanValue,
            nik_ibu = nikIbuValue,
            nama_ibu = namaIbuValue,
            tempat_lahir_ibu = tempatLahirIbuValue,
            tanggal_lahir_ibu = tanggalLahirIbuValue,
            agama_ibu_id = agamaIbuIdValue,
            kewarganegaraan_ibu_id = kewarganegaraanIbuIdValue,
            pekerjaan_ibu = pekerjaanIbuValue,
            alamat_ibu = alamatIbuValue,
            lama_dikandung = lamaDikandungValue,
            tanggal_meninggal = tanggalMeninggalValue,
            tempat_meninggal = tempatMeninggalValue,
            hubungan_id = hubunganIdValue
        )
    }
}