package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.cerai

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response.UserInfoResponse
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.data.remote.dto.referensi.AgamaResponse
import com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan.SPMCeraiRequest

class SPMCeraiStateManager {
    // Form Data States - Step 1 (Suami)
    var nikSuamiValue by mutableStateOf("")
        private set
    var namaSuamiValue by mutableStateOf("")
        private set
    var alamatSuamiValue by mutableStateOf("")
        private set
    var pekerjaanSuamiValue by mutableStateOf("")
        private set
    var tanggalLahirSuamiValue by mutableStateOf("")
        private set
    var tempatLahirSuamiValue by mutableStateOf("")
        private set
    var agamaIdSuamiValue by mutableStateOf("")
        private set

    // Form Data States - Step 2 (Istri)
    var nikIstriValue by mutableStateOf("")
        private set
    var namaIstriValue by mutableStateOf("")
        private set
    var alamatIstriValue by mutableStateOf("")
        private set
    var pekerjaanIstriValue by mutableStateOf("")
        private set
    var tanggalLahirIstriValue by mutableStateOf("")
        private set
    var tempatLahirIstriValue by mutableStateOf("")
        private set
    var agamaIdIstriValue by mutableStateOf("")
        private set

    // Form Data States - Step 3 (Pelengkap)
    var sebabCeraiValue by mutableStateOf("")
        private set
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

    // Step 1 Update Functions - Suami
    fun updateNikSuami(value: String) { nikSuamiValue = value }
    fun updateNamaSuami(value: String) { namaSuamiValue = value }
    fun updateAlamatSuami(value: String) { alamatSuamiValue = value }
    fun updatePekerjaanSuami(value: String) { pekerjaanSuamiValue = value }
    fun updateTanggalLahirSuami(value: String) { tanggalLahirSuamiValue = value }
    fun updateTempatLahirSuami(value: String) { tempatLahirSuamiValue = value }
    fun updateAgamaIdSuami(value: String) { agamaIdSuamiValue = value }

    // Step 2 Update Functions - Istri
    fun updateNikIstri(value: String) { nikIstriValue = value }
    fun updateNamaIstri(value: String) { namaIstriValue = value }
    fun updateAlamatIstri(value: String) { alamatIstriValue = value }
    fun updatePekerjaanIstri(value: String) { pekerjaanIstriValue = value }
    fun updateTanggalLahirIstri(value: String) { tanggalLahirIstriValue = value }
    fun updateTempatLahirIstri(value: String) { tempatLahirIstriValue = value }
    fun updateAgamaIdIstri(value: String) { agamaIdIstriValue = value }

    // Step 3 Update Functions - Pelengkap
    fun updateSebabCerai(value: String) { sebabCeraiValue = value }
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
        // Populate to Suami data (assuming user is the husband)
        nikSuamiValue = userData.nik ?: ""
        namaSuamiValue = userData.nama_warga ?: ""
        alamatSuamiValue = userData.alamat ?: ""
        pekerjaanSuamiValue = userData.pekerjaan ?: ""
        tanggalLahirSuamiValue = dateFormatterToApiFormat(userData.tanggal_lahir ?: "")
        tempatLahirSuamiValue = userData.tempat_lahir ?: ""
        agamaIdSuamiValue = userData.agama_id ?: ""
    }

    fun clearUserData() {
        // Clear Suami data
        nikSuamiValue = ""
        namaSuamiValue = ""
        alamatSuamiValue = ""
        pekerjaanSuamiValue = ""
        tanggalLahirSuamiValue = ""
        tempatLahirSuamiValue = ""
        agamaIdSuamiValue = ""
    }

    fun resetForm() {
        currentStep = 1
        useMyDataChecked = false
        clearUserData()

        // Clear Istri data
        nikIstriValue = ""
        namaIstriValue = ""
        alamatIstriValue = ""
        pekerjaanIstriValue = ""
        tanggalLahirIstriValue = ""
        tempatLahirIstriValue = ""
        agamaIdIstriValue = ""

        // Clear Pelengkap data
        sebabCeraiValue = ""
        keperluanValue = ""

        errorMessage = null
        showConfirmationDialog = false
        showPreviewDialog = false
    }

    fun hasFormData(): Boolean {
        return nikSuamiValue.isNotBlank() || namaSuamiValue.isNotBlank() ||
                alamatSuamiValue.isNotBlank() || pekerjaanSuamiValue.isNotBlank() ||
                tanggalLahirSuamiValue.isNotBlank() || tempatLahirSuamiValue.isNotBlank() ||
                agamaIdSuamiValue.isNotBlank() || nikIstriValue.isNotBlank() ||
                namaIstriValue.isNotBlank() || alamatIstriValue.isNotBlank() ||
                pekerjaanIstriValue.isNotBlank() || tanggalLahirIstriValue.isNotBlank() ||
                tempatLahirIstriValue.isNotBlank() || agamaIdIstriValue.isNotBlank() ||
                sebabCeraiValue.isNotBlank() || keperluanValue.isNotBlank()
    }

    fun toRequest(): SPMCeraiRequest {
        return SPMCeraiRequest(
            agama_id_istri = agamaIdIstriValue,
            agama_id_suami = agamaIdSuamiValue,
            alamat_istri = alamatIstriValue,
            alamat_suami = alamatSuamiValue,
            keperluan = keperluanValue,
            nama_istri = namaIstriValue,
            nama_suami = namaSuamiValue,
            nik_istri = nikIstriValue,
            nik_suami = nikSuamiValue,
            pekerjaan_istri = pekerjaanIstriValue,
            pekerjaan_suami = pekerjaanSuamiValue,
            sebab_cerai = sebabCeraiValue,
            tanggal_lahir_istri = tanggalLahirIstriValue,
            tanggal_lahir_suami = tanggalLahirSuamiValue,
            tempat_lahir_istri = tempatLahirIstriValue,
            tempat_lahir_suami = tempatLahirSuamiValue
        )
    }
}